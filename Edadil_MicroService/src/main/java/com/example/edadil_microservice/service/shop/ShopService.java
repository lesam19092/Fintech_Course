package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.model.entity.Shop;

import java.util.Set;

public interface ShopService {

    Set<Shop> findShopsByCompanyIdAndCity(Integer companyId, String city);

    Shop findShopByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId);

    Shop findShopProductsByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId);

    Shop findShopByNameOfCompanyIdAndId(Integer companyId, Integer shopId);

}
