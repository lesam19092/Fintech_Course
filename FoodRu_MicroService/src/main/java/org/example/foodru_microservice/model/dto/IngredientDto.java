package org.example.foodru_microservice.model.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientDto {
    private String name;
    private Double measure;
}
