package org.example.foodru_microservice.service.upload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final AmazonS3 s3Client;
        @Value("${spring.s3.bucketName}")
    private String bucketName;

    @Override
    public void uploadPdf(byte[] pdf) {

        try {
            String fileName = generateUniqueName();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(pdf.length);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdf)) {
                s3Client.putObject(bucketName, fileName, inputStream, metadata);
                log.info("Upload Service. Added file: " + fileName + " to bucket: " + bucketName);
            }
        } catch (IOException | AmazonS3Exception e) {
            log.error("Amazon S3 error uploading photos to Object Storage. Reason: {}", e.getMessage());
        }
    }

    // TODO: Implement userId-date-uuid format for file names
    private String generateUniqueName() {
        UUID uuid = UUID.randomUUID();
        return uuid +".pdf";
    }
}