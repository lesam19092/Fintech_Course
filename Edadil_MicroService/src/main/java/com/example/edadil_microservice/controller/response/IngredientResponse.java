package com.example.edadil_microservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientResponse {
    private String name;
    private Double measure;
    private String firm;
    private Double price;
}
