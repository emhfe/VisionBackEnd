package com.co.vision.fund.services;

import com.co.vision.fund.entity.FileMetadata;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileMetadataService {

    FileMetadata uploadFile(MultipartFile file) throws IOException;

    void deleteFile(Long id);

    FileMetadata updateFile(Long id, MultipartFile file) throws IOException;
}
