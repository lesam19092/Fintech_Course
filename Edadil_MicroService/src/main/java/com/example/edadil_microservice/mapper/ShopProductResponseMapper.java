package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ProductResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class ShopProductResponseMapper {

    public static Set<ProductResponse> mapProductsToProductResponses(Set<ShopProduct> products) {
        return products.stream()
                .map(ShopProductResponseMapper::mapProductToProductResponse)
                .collect(Collectors.toSet());
    }

    public static ProductResponse mapProductToProductResponse(ShopProduct products) {
        return ProductResponse.builder()
                .name(products.getProduct().getName())
                .firm(products.getProduct().getFirm().getFirmName())
                .count(products.getCount())
                .price(products.getPrice()).build();
    }
}