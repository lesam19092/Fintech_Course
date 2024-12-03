package org.example.foodru_microservice.service.kafka;

import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;

public interface KafkaConsumer {

    PaymentReceiptResponse getResponse();

}
