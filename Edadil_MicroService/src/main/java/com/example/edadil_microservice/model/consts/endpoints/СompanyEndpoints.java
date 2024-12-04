package com.example.edadil_microservice.model.consts.endpoints;

public interface СompanyEndpoints {
    String GET_ALL_COMPANIES = "/companies";
    String GET_COMPANY_BY_ID = "/companies/{companyId}";
    String GET_COMPANIES_HAVING_FIRM_PRODUCTS = "/firms/{firmId}/company";
    String GET_COMPANIES_HAVING_FIRM_PRODUCTS_BY_ID = "/firms/{firmId}/company/{companyId}";
}

