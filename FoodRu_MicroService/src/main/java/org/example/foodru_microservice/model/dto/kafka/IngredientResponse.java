package org.example.foodru_microservice.model.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private String name;
    private Double measure;
    private String firm;
    private Double price;
}