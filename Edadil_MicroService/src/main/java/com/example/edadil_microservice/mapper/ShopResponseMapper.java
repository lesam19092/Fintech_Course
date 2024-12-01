package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.response.ShopResponse;
import com.example.edadil_microservice.entity.Shop;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Component
public class ShopResponseMapper {


    public Set<ShopResponse> convertShopsToShopResponses(Set<Shop> shops) {

        requireNonEmptyCollection(shops);

        return shops.stream()
                .map(this::buildShopResponse)
                .collect(Collectors.toSet());
    }


    public ShopResponse buildShopResponse(Shop shop) {
        return ShopResponse.builder()
                .companyName(shop.getNameOfCompany().getCompanyName())
                .id(shop.getId())
                .city(shop.getCity())
                .address(shop.getAddress())
                .build();
    }
}
