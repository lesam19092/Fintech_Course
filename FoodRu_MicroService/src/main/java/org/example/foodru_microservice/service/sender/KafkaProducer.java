package org.example.foodru_microservice.service.sender;

import org.example.foodru_microservice.model.dto.IngredientDto;

public interface KafkaProducer {

    void sendMessage(IngredientDto ingredientDto);

}
