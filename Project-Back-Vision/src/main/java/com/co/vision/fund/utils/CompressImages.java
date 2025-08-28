package com.co.vision.fund.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class CompressImages {

    public byte[] compressImage(MultipartFile file) throws IOException {
        final int maxSizeBytes = 1 * 1024 * 1024;
        double quality = 0.9;
        byte[] bestAttempt = null;

        byte[] originalBytes = file.getBytes();
        BufferedImage originalImage;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(originalBytes)) {
            originalImage = ImageIO.read(bais);
        }

        while (quality >= 0.1) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                Thumbnails.of(originalImage)
                        .scale(1.0)
                        .outputQuality(quality)
                        .outputFormat("jpg")
                        .toOutputStream(outputStream);

                byte[] compressedBytes = outputStream.toByteArray();

                if (bestAttempt == null || compressedBytes.length < bestAttempt.length) {
                    bestAttempt = compressedBytes;
                }

                if (compressedBytes.length <= maxSizeBytes) {
                    return compressedBytes;
                }

                quality -= 0.1;
            }
        }
        return bestAttempt;
    }
}
