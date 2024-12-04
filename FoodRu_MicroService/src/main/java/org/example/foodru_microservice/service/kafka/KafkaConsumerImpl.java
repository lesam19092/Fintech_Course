package org.example.foodru_microservice.service.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@Data
@Slf4j
public class KafkaConsumerImpl implements KafkaConsumer {

    private final BlockingQueue<PaymentReceiptResponse> responseQueue = new LinkedBlockingQueue<>();


    @KafkaListener(topics = "${spring.properties.kafka.topicEdadilToFoodRu}"
            , groupId = "${spring.properties.kafka.groupId}")
    private void listenGroupFoo(PaymentReceiptResponse paymentReceiptResponse) {

        try {
            responseQueue.put(paymentReceiptResponse);
            log.info("Received Message in group foo: {}", paymentReceiptResponse);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public PaymentReceiptResponse getResponse() {
        try {
            PaymentReceiptResponse response = responseQueue.poll(2_000, TimeUnit.MILLISECONDS);
            if (response == null) {
                log.error("Error getting response from Kafka Consumer. Reason: Timeout");
                throw new EntitySearchException("receipt not found");
            }
            return response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error getting response from Kafka Consumer. Reason: {}", e.getMessage());
            throw new EntitySearchException("Thread interrupted while waiting for Kafka response.");
        }
    }

}