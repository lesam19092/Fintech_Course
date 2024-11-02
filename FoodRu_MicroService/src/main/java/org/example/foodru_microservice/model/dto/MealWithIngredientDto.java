package org.example.foodru_microservice.model.dto;


import lombok.Builder;
import lombok.Data;
import org.example.foodru_microservice.model.dto.kafka.IngredientDto;
import java.util.List;

@Data
@Builder
public class MealWithIngredientDto {

    private Integer id;
    private String name;
    private String cookInstructions;
    private List<IngredientDto> ingredients;


}
