package org.example.foodru_microservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MealDto {
    private Integer id;
    private String name;
    private String cookInstructions;

}
