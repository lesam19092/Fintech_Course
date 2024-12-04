package org.example.foodru_microservice.service.pdf;

import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface PdfService {

    void generateAndSendPdfReport(PaymentReceiptResponse response, User user) throws IOException;
}
