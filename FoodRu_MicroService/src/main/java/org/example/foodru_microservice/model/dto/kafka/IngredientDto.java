package org.example.foodru_microservice.model.dto.kafka;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class IngredientDto {
    private String name;
    private Double measure;
}
