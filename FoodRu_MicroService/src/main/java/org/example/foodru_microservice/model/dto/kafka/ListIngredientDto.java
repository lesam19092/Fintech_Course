package org.example.foodru_microservice.model.dto.kafka;

import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class ListIngredientDto {

    private List<IngredientDto> ingredientDtoList;

}
