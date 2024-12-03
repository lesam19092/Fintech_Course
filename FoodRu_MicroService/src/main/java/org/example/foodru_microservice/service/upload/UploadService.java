package org.example.foodru_microservice.service.upload;


public interface UploadService {

    void uploadPdf(byte[] pdf,Long userId);
}