package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.controller.dto.ShopDto;

import java.util.List;

public interface ShopService {


    List<ShopDto> findCompanyShops(Integer companyId);

    List<ShopDto> findCompanyShopsInCity(Integer companyId, String city);

    ShopDto findCompanyShopInCityById(Integer companyId, String city, Integer shopId);

    ShopDto findShopInCompanyWithFirmProductsById(Integer firmId, Integer companyId, Integer shopId);

    List<ShopDto> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId);

    List<Integer> getIdShops();
}


