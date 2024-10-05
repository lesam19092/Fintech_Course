package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ShopResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class ShopResponseMapper {

    public static Set<ShopResponse> mapShopsToShopResponses(Set<Shop> shops) {
        return shops.stream()
                .map(ShopResponseMapper::mapShopToShopResponse)
                .collect(Collectors.toSet());
    }


    public static ShopResponse mapShopToShopResponse(Shop shop) {
        return ShopResponse.builder()
                .companyName(shop.getNameOfCompany().getCompanyName())
                .id(shop.getId())
                .city(shop.getCity())
                .address(shop.getAddress())
                .build();
    }
}
