package org.example.foodru_microservice.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MealDto {
    private Long id;
    private String name;
    private String cookInstructions;

}
