package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ShopProductResponseMapper {

    public static Set<ProductResponse> convertShopProductsToProductResponses(Set<ShopProduct> products) {
        Set<ProductResponse> set = products.stream()
                .map(ShopProductResponseMapper::convertShopProductToProductResponse)
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(set)) {
            return set;
        }
        log.error("Empty collection of Products ");
        throw new NoSuchElementException("Empty collection of Products");
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
                .products(ShopProductResponseMapper.convertShopProductsToProductResponses(shop.getShopproducts()))
                .build();
    }

    public static ShopProductResponse buildShopProductResponseWithSingleProduct(ShopProduct shopProduct) {
        return ShopProductResponse.builder()
                .shop(ShopResponseMapper.buildShopResponse(shopProduct.getShop()))
                .products(Set.of(convertShopProductToProductResponse(shopProduct)))
                .build();

    }
}