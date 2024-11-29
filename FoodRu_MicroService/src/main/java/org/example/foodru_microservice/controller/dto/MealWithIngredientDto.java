package org.example.foodru_microservice.controller.dto;


import lombok.Builder;
import lombok.Data;
import org.example.foodru_microservice.service.kafka.dto.IngredientDto;
import java.util.List;

@Data
@Builder
public class MealWithIngredientDto {

    private Long id;
    private String name;
    private String cookInstructions;
    private List<IngredientDto> ingredients;


}
