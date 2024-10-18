package com.example.edadil_microservice.model.response;

import lombok.Data;

@Data
public class IngredientResponse {
    private String name;
    private int count;
    private String firm;
    private double price;
}
