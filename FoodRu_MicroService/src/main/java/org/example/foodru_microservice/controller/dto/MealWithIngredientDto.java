package org.example.foodru_microservice.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.foodru_microservice.service.kafka.dto.IngredientDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealWithIngredientDto {
    private Long id;
    private String name;
    private String cookInstructions;
    private List<IngredientDto> ingredients;
}
