package com.example.edadil_microservice.controller.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ListIngredientDto {

    private List<IngredientDto> ingredientDtoList;

}