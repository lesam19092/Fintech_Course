package com.example.edadil_microservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientResponse {
    private String name;
    private int count;
    private String firm;
    private double price;
}
