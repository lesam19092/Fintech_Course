package com.example.edadil_microservice.model.consts.endpoints;

public interface ShopProductEndpoints {
    String GET_COMPANY_SHOP_PRODUCTS = "/companies/{companyId}/shops/{city}/{shopId}/products";
    String GET_SHOPS_WITH_PRODUCT = "/firms/{firmId}/products/{productId}/shops";
    String GET_FIRM_PRODUCTS_IN_SHOP = "/firms/{firmId}/company/{companyId}/shops/{shopId}/products";
}