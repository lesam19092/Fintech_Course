package org.example.foodru_microservice.service.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
            log.info("Received Message in group foo: " + paymentReceiptResponse.toString());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public PaymentReceiptResponse getResponse(long timeout, TimeUnit unit) {
        try {
            return responseQueue.poll(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Error getting response from Kafka Consumer. Reason: {}", e.getMessage());
            return null;
        }
    }

}