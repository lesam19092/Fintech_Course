package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Slf4j
public class ShopProductResponseMapper {

    public static Set<ProductResponse> convertShopProductsToProductResponses(Set<ShopProduct> products) {

        return requireNonEmptyCollection(products).stream()
                .map(ShopProductResponseMapper::convertShopProductToProductResponse)
                .collect(Collectors.toSet());

    }

    public static ProductResponse convertShopProductToProductResponse(ShopProduct products) {
        return ProductResponse.builder()
                .name(products.getProduct().getName())
                .firm(products.getProduct().getFirm().getFirmName())
                .count(products.getCount())
                .price(products.getPrice()).build();
    }

    public static ShopProductResponse buildShopProductResponse(Shop shop) {
        return ShopProductResponse.builder()
                .shop(ShopResponseMapper.buildShopResponse(shop))
                .products(ShopProductResponseMapper.convertShopProductsToProductResponses(shop.getShopProducts()))
                .build();
    }

    public static ShopProductResponse buildShopProductResponseWithSingleProduct(ShopProduct shopProduct) {
        return ShopProductResponse.builder()
                .shop(ShopResponseMapper.buildShopResponse(shopProduct.getShop()))
                .products(Set.of(convertShopProductToProductResponse(shopProduct)))
                .build();

    }
}