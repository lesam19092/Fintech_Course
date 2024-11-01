package org.example.foodru_microservice.service.sender;

import org.example.foodru_microservice.model.dto.ListIngredientDto;

public interface KafkaProducer {

    void sendMessage(ListIngredientDto listIngredientDto);

}
