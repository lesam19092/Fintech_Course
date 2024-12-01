package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.entity.ShopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Component
@RequiredArgsConstructor
public class ShopProductMapper {

    private final ShopMapper ShopMapper;

    public Set<ProductDto> toProductsDto(Set<ShopProduct> products) {

        return requireNonEmptyCollection(products).stream()
                .map(this::toProductDto)
                .collect(Collectors.toSet());

    }

    public ProductDto toProductDto(ShopProduct products) {
        return ProductDto.builder()
                .name(products.getProduct().getName())
                .firm(products.getProduct().getFirm().getFirmName())
                .count(products.getCount())
                .price(products.getPrice()).build();
    }

    public ShopProductDto buildShopProductResponse(Shop shop) {
        return ShopProductDto.builder()
                .shop(ShopMapper.toDto(shop))
                .products(toProductsDto(shop.getShopProducts()))
                .build();
    }

    public ShopProductDto toShopProductDto(ShopProduct shopProduct) {
        return ShopProductDto.builder()
                .shop(ShopMapper.toDto(shopProduct.getShop()))
                .products(Set.of(toProductDto(shopProduct)))
                .build();

    }
}