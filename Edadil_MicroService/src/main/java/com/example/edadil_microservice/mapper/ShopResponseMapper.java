package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ShopResponse;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopResponseMapper {

    public static Set<ShopResponse> mapShopsToShopResponses(Set<Shop> shops) {
        Set<ShopResponse> set = shops.stream()
                .map(ShopResponseMapper::mapShopToShopResponse)
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(set)) {
            return set;
        }
        throw new NoSuchElementException("Empty collection");
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
