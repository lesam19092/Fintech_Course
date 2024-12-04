package com.example.edadil_microservice.controller.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopDto {
    private String companyName;
    private Long id;
    private String city;
    private String address;
}

