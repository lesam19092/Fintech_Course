package com.example.edadil_microservice.service.kafka;

import com.example.edadil_microservice.config.kafka.KafkaPropertiesConfig;
import com.example.edadil_microservice.controller.response.PaymentReceiptResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class KafkaProducerImpl implements KafkaProducer {


    private final KafkaPropertiesConfig config;

    private final KafkaTemplate<String, PaymentReceiptResponse> kafkaTemplate;

    @Override
    public void sendMessage(PaymentReceiptResponse listIngredientDto) {
        kafkaTemplate.send(config.getTopicEdadilToFoodRu(), listIngredientDto);
        log.info("Kafka Producer. Sent message: {}", listIngredientDto.toString());
    }
}