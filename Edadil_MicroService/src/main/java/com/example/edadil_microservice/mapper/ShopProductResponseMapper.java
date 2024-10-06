package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import org.hibernate.mapping.Collection;
import org.springframework.util.CollectionUtils;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopProductResponseMapper {

    public static Set<ProductResponse> mapProductsToProductResponses(Set<ShopProduct> products) {
        Set<ProductResponse> set = products.stream()
                .map(ShopProductResponseMapper::mapProductToProductResponse)
                .collect(Collectors.toSet());

        if (!CollectionUtils.isEmpty(set)) {
            return set;
        }
        throw new NoSuchElementException("Empty collection of Products");
    }

    public static ProductResponse mapProductToProductResponse(ShopProduct products) {
        return ProductResponse.builder()
                .name(products.getProduct().getName())
                .firm(products.getProduct().getFirm().getFirmName())
                .count(products.getCount())
                .price(products.getPrice()).build();
    }

    public static ShopProductResponse mapShopToShopProductResponse(Shop shop) {
        return ShopProductResponse.builder()
                .shop(ShopResponseMapper.mapShopToShopResponse(shop))
                .products(ShopProductResponseMapper.mapProductsToProductResponses(shop.getShopproducts()))
                .build();

    }


    public static ProductResponse mapProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .firm(product.getFirm().getFirmName())
                .build();
    }
}