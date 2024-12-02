package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.ProductDetailsDto;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.model.entity.ShopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopProductMapper {

    private final ShopMapper ShopMapper;


    public ProductDetailsDto toProductDto(ShopProduct products) {
        return ProductDetailsDto.builder()
                .name(products.getProduct().getName())
                .firm(products.getProduct().getFirm().getFirmName())
                .count(products.getCount())
                .price(products.getPrice()).build();
    }


    public ShopProductDto toShopProductDto(ShopProduct shopProduct) {
        return ShopProductDto.builder()
                .shop(ShopMapper.toDto(shopProduct.getShop()))
                .products(List.of(toProductDto(shopProduct)))
                .build();

    }

    public List<ShopProductDto> toShopProductDtoList(List<ShopProduct> shopProducts) {

        return shopProducts.stream()
                .map(this::toShopProductDto)
                .collect(Collectors.toList());


    }
}