package com.example.edadil_microservice.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ShopProductResponse {
    private ShopResponse shop;
    private Set<ProductResponse> products;
}
