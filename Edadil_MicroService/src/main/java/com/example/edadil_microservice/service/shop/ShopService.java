package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.controller.dto.ShopDto;

import java.util.List;

public interface ShopService {


    List<ShopDto> findCompanyShops(Long companyId);

    List<ShopDto> findCompanyShopsInCity(Long companyId, String city);

    ShopDto findCompanyShopInCityById(Long companyId, String city, Long shopId);

    ShopDto findShopInCompanyWithFirmProductsById(Long firmId, Long companyId, Long shopId);

    List<ShopDto> findShopsInCompanyWithFirmProducts(Long firmId, Long companyId);

    List<Long> getIdShops();
}



