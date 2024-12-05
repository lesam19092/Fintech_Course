package com.example.edadil_microservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientDto {
    private String name;
    private Double measure;
}

