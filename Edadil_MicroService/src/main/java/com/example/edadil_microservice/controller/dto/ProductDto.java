package com.example.edadil_microservice.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Integer id;
    private String name;

}