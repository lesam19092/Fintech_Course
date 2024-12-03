package org.example.foodru_microservice.service.kafka;

import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;

import java.util.concurrent.TimeUnit;

public interface KafkaConsumer {

    PaymentReceiptResponse getResponse(long timeout, TimeUnit unit);

}
