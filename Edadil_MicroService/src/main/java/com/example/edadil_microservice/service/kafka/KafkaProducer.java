package com.example.edadil_microservice.service.kafka;

import com.example.edadil_microservice.service.kafka.response.PaymentReceiptResponse;

public interface KafkaProducer {

    void sendMessage(PaymentReceiptResponse listIngredientDto);

}
