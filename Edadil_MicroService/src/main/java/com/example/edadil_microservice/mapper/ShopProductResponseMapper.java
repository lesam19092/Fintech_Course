package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.response.ProductResponse;
import com.example.edadil_microservice.controller.response.ShopProductResponse;
import com.example.edadil_microservice.entity.Shop;
import com.example.edadil_microservice.entity.ShopProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Component
@RequiredArgsConstructor
public class ShopProductResponseMapper {

    private final ShopResponseMapper ShopResponseMapper;

    public Set<ProductResponse> convertShopProductsToProductResponses(Set<ShopProduct> products) {

        return requireNonEmptyCollection(products).stream()
                .map(this::convertShopProductToProductResponse)
                .collect(Collectors.toSet());

    }

    public ProductResponse convertShopProductToProductResponse(ShopProduct products) {
        return ProductResponse.builder()
                .name(products.getProduct().getName())
                .firm(products.getProduct().getFirm().getFirmName())
                .count(products.getCount())
                .price(products.getPrice()).build();
    }

    public ShopProductResponse buildShopProductResponse(Shop shop) {
        return ShopProductResponse.builder()
                .shop(ShopResponseMapper.buildShopResponse(shop))
                .products(convertShopProductsToProductResponses(shop.getShopProducts()))
                .build();
    }

    public ShopProductResponse buildShopProductResponseWithSingleProduct(ShopProduct shopProduct) {
        return ShopProductResponse.builder()
                .shop(ShopResponseMapper.buildShopResponse(shopProduct.getShop()))
                .products(Set.of(convertShopProductToProductResponse(shopProduct)))
                .build();

    }
}