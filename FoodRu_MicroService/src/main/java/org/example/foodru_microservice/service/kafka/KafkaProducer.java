package org.example.foodru_microservice.service.kafka;

import org.example.foodru_microservice.model.dto.kafka.ListIngredientDto;

public interface KafkaProducer {

    void sendMessage(ListIngredientDto listIngredientDto);

}
