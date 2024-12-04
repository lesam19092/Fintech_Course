package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Component
public class ShopMapper {


    public List<ShopDto> toDtoList(List<Shop> shops) {

        requireNonEmptyCollection(shops);

        return shops.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public ShopDto toDto(Shop shop) {
        return ShopDto.builder()
                .companyName(shop.getNameOfCompany().getCompanyName())
                .id(shop.getId())
                .city(shop.getCity())
                .address(shop.getAddress())
                .build();
    }
}

