package com.example.edadil_microservice.service.kafka;

import com.example.edadil_microservice.model.response.PaymentReceiptResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
@ConfigurationProperties("spring.properties.kafka")
public class KafkaProducerImpl implements KafkaProducer {


    private String topicEdadilToFoodRu;

    private final KafkaTemplate<String, PaymentReceiptResponse> kafkaTemplate;

    @Override
    public void sendMessage(PaymentReceiptResponse listIngredientDto) {
        kafkaTemplate.send(topicEdadilToFoodRu, listIngredientDto);
        log.info("Kafka Producer. Sent message: " + listIngredientDto.toString());
    }
}
