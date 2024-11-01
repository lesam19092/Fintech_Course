package org.example.foodru_microservice.service.sender;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.model.request.IngredientRequest;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
@ConfigurationProperties("spring.properties.kafka")
public class KafkaProducerImpl implements KafkaProducer {


    private String topicFoodRuToEdadil;

    private final KafkaTemplate<String, IngredientRequest> kafkaTemplate;

    @Override
    public void sendMessage(String msg) {
        IngredientRequest ingredientRequest = new IngredientRequest();
        ingredientRequest.setName(msg);
        ingredientRequest.setCount(10);
        kafkaTemplate.send(topicFoodRuToEdadil, ingredientRequest);
        log.info("Kafka Producer. Sent message: " + msg);
    }

}
