package com.example.edadil_microservice.model.consts.endpoints;

public interface ShopEndpoints {
    String GET_COMPANY_SHOPS = "/companies/{companyId}/shops";
    String GET_COMPANY_SHOPS_BY_CITY = "/companies/{companyId}/shops/{city}";
    String GET_COMPANY_SHOP_FROM_CITY_BY_ID = "/companies/{companyId}/shops/{city}/{shopId}";
    String GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS = "/firms/{firmId}/company/{companyId}/shops";
    String GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS_BY_ID = "/firms/{firmId}/company/{companyId}/shops/{shopId}";
}