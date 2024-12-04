package com.example.edadil_microservice.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShopProductDto {
    private ShopDto shop;
    private List<ProductDetailsDto> products;
}

