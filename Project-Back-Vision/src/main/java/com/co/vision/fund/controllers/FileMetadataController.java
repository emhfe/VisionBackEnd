package com.co.vision.fund.controllers;

import com.co.vision.fund.entity.FileMetadata;
import com.co.vision.fund.services.FileMetadataService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("visionfund/api/images")
public class FileMetadataController {

    private final FileMetadataService service;

    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(@RequestParam("file") MultipartFile file)
    throws IOException {
        FileMetadata guardado = service.uploadFile(file);
        return ResponseEntity.ok(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FileMetadata> update(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file) throws IOException {
        FileMetadata fileUpdated = service.updateFile(id, file);
        return ResponseEntity.ok(fileUpdated);
    }
}
