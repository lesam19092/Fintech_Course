package com.example.edadil_microservice.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String name;
    private String firm;
    private Integer count;
    private Double price;

}