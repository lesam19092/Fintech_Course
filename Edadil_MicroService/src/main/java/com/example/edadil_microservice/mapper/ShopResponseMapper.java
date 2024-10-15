package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ShopResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ShopResponseMapper {

    public static Set<ShopResponse> convertShopsToShopResponses(Set<Shop> shops) {
        Set<ShopResponse> set = shops.stream()
                .map(ShopResponseMapper::buildShopResponse)
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(set)) {
            return set;
        }
        log.error("Empty collection of Shops");
        throw new NoSuchElementException("Empty collection");
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
