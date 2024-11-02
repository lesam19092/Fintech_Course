package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ShopResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Slf4j
public class ShopResponseMapper {


    public static Set<ShopResponse> convertShopsToShopResponses(Set<Shop> shops) {

        requireNonEmptyCollection(shops);

        return shops.stream()
                .map(ShopResponseMapper::buildShopResponse)
                .collect(Collectors.toSet());
    }


    public static ShopResponse buildShopResponse(Shop shop) {
        return ShopResponse.builder()
                .companyName(shop.getNameOfCompany().getCompanyName())
                .id(shop.getId())
                .city(shop.getCity())
                .address(shop.getAddress())
                .build();
    }
}
