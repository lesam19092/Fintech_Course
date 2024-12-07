package org.example.foodru_microservice.service.kafka;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class KafkaConsumerImplTest extends IntegrationTestBase {

    KafkaConsumerImpl kafkaConsumer = new KafkaConsumerImpl();

    @Test
    void getResponse() throws InterruptedException {
        PaymentReceiptResponse expectedResponse = new PaymentReceiptResponse();
        kafkaConsumer.getResponseQueue().put(expectedResponse);
        PaymentReceiptResponse actualResponse = kafkaConsumer.getResponse();
        assertEquals(expectedResponse, actualResponse);
    }
}