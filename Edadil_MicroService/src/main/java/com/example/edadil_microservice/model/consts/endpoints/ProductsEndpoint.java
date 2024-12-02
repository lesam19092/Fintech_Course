package com.example.edadil_microservice.model.consts.endpoints;

public interface ProductsEndpoint {
    String GET_FIRM_PRODUCTS = "/firms/{firmId}/products";
    String GET_FIRM_PRODUCT_BY_ID = "/firms/{firmId}/products/{productId}";
}