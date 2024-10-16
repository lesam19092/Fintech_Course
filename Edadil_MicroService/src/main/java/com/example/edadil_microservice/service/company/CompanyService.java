package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;

import java.util.List;
import java.util.Set;

public interface CompanyService {

    List<Company> findAllCompanies();

    Company findCompanyById(Integer companyId);

    Set<ShopResponse> findCompanyShops(Integer companyId);

    Set<ShopResponse> findCompanyShopsInCity(Integer companyId, String city);

    ShopResponse findCompanyShopInCityById(Integer companyId, String city, Integer shopId);

    ShopProductResponse retrieveShopProducts(Integer companyId, String city, Integer shopId);

    ShopProductResponse retrieveShopProducts(Integer companyId, Integer shopId);

    ShopProductResponse findSpecificProductInShop(Integer companyId, String city, Integer shopId, String name);

    List<ShopProductResponse> getAllShopsWithProducts();
}
