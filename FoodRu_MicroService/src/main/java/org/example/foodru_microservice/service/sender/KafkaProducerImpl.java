package org.example.foodru_microservice.service.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.model.IngredientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, IngredientRequest> kafkaTemplate;

    @Autowired
    public KafkaProducerImpl(KafkaTemplate<String, IngredientRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String msg) {
        IngredientRequest ingredientRequest = new IngredientRequest();
        ingredientRequest.setName(msg);
        ingredientRequest.setCount(10);
        kafkaTemplate.send("topicFoodRuToEdadil", ingredientRequest);
        log.info("Kafka Producer. Sent message: " + msg);
    }

//todo вынести топики в проперти
}
