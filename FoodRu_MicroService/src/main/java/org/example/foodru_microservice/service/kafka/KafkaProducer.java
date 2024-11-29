package org.example.foodru_microservice.service.kafka;

import org.example.foodru_microservice.service.kafka.dto.ListIngredientDto;

public interface KafkaProducer {

    void sendMessage(ListIngredientDto listIngredientDto);

}
