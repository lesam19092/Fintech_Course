package org.example.foodru_microservice.model.request;

import lombok.Data;


@Data
public class IngredientRequest {
    private String name;
    private int count;
}