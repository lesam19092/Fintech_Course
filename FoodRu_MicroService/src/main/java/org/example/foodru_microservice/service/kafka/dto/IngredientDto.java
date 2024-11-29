package org.example.foodru_microservice.service.kafka.dto;


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
