package com.co.vision.fund.services.impl;

import com.co.vision.fund.Repository.FileMetadataRepository;
import com.co.vision.fund.entity.FileMetadata;
import com.co.vision.fund.services.FileMetadataService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

import com.co.vision.fund.utils.CompressImages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class FileMetadataServiceImpl implements FileMetadataService {

    private final FileMetadataRepository fileRepository;

    private final S3Client s3Client;

    private final Region region;

    private final CompressImages compressImages;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Override
    public FileMetadata uploadFile(MultipartFile file) throws IOException {

        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
        byte[] fileBytes;

        if (file.getContentType() != null) {
            fileBytes = compressImages.compressImage(file);
        } else {
            fileBytes = file.getBytes();
        }

        try(InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, fileBytes.length));

            String url = String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region.toString(), key);

            FileMetadata archivo = FileMetadata.builder()
                .originalFileName(file.getOriginalFilename())
                .url(url)
                .build();

            return fileRepository.save(archivo);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void deleteFile(Long id) {
        Optional<FileMetadata> archivo = fileRepository.findById(id);

        if (archivo.isPresent()) {
            String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/",
                    bucketName, region.toString());

            String key = archivo.get().getUrl().replace(bucketUrlPrefix, "");

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteRequest);

            fileRepository.deleteById(id);
        }

    }

    @Override
    public FileMetadata updateFile(Long id, MultipartFile file) throws IOException {
        FileMetadata archivoExistente = fileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));

        String bucketUrlPrefix = String.format("https://%s.s3.%s.amazonaws.com/",
            bucketName, region.toString());
        String oldKey = archivoExistente.getUrl().replace(bucketUrlPrefix, "");

        s3Client.deleteObject(DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(oldKey)
            .build());

        String newKey = UUID.randomUUID() + "_" + file.getOriginalFilename();
        byte[] fileBytes;

        if (file.getContentType() != null) {
            fileBytes = compressImages.compressImage(file);
        } else {
            fileBytes = file.getBytes();
        }

        try(InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(newKey)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, fileBytes.length));

        } catch (IOException e) {
            throw new RuntimeException("Error updating file on S3", e);
        }

        String nuevaUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
            bucketName, region.toString(), newKey);

        archivoExistente.setOriginalFileName(file.getOriginalFilename());
        archivoExistente.setUrl(nuevaUrl);

        return fileRepository.save(archivoExistente);
    }
}
