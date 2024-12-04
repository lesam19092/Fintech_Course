package com.example.edadil_microservice.controller.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsDto {
    private String name;
    private String firm;
    private Integer count;
    private Double price;
}
