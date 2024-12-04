package org.example.foodru_microservice.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto {
    private Long id;
    private String name;
}