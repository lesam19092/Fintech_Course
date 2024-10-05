package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;

import java.util.List;
import java.util.Set;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company getCompanyById(Integer companyId);

    Set<ShopResponse> getCompanyShops(Integer companyId);

    Set<ShopResponse> getCompanyShopsByCity(Integer companyId, String city);


    ShopResponse getCompanyShopsByCityAndShopId(Integer companyId, String city, Integer shopId);


    ShopProductResponse getCompanyShopProducts(Integer companyId, String city, Integer shopId);
}
