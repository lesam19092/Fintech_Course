package org.example.foodru_microservice.service.upload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

class UploadServiceImplTest {

    @Mock
    private AmazonS3 s3Client;

    @InjectMocks
    private UploadServiceImpl uploadService;

    @Value("${spring.s3.bucketName:test-bucket}")
    private String bucketName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadPdf() {
        byte[] pdf = new byte[]{1, 2, 3};
        Long userId = 123L;
        String fileName = userId + "-2023-10-10-uuid.pdf";

        doAnswer(invocation -> null).when(s3Client).putObject(eq(bucketName), eq(fileName), any(ByteArrayInputStream.class), any(ObjectMetadata.class));
        uploadService.uploadPdf(pdf, userId);

        ArgumentCaptor<ByteArrayInputStream> inputStreamCaptor = ArgumentCaptor.forClass(ByteArrayInputStream.class);
        ArgumentCaptor<ObjectMetadata> metadataCaptor = ArgumentCaptor.forClass(ObjectMetadata.class);
        ArgumentCaptor<String> fileNameCaptor = ArgumentCaptor.forClass(String.class);

        verify(s3Client).putObject(eq(bucketName), fileNameCaptor.capture(), inputStreamCaptor.capture(), metadataCaptor.capture());

        String capturedFileName = fileNameCaptor.getValue();

        assertTrue(capturedFileName.matches("^\\d+-\\d{4}-\\d{2}-\\d{2}-[a-f0-9\\-]+\\.pdf$"));

        assertEquals(pdf.length, metadataCaptor.getValue().getContentLength());
    }
}
