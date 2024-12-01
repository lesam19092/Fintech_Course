package com.example.edadil_microservice.controller.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopResponse {
    private String companyName;
    private int id;
    private String city;
    private String address;
}
