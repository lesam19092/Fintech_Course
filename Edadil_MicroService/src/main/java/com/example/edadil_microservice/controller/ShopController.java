package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ShopEndpoints.*;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping(GET_COMPANY_SHOPS)
    public List<ShopDto> getCompanyShops(@PathVariable Integer companyId) {
        return shopService.findCompanyShops(companyId);
    }

    @GetMapping(GET_COMPANY_SHOPS_BY_CITY)
    public List<ShopDto> getCompanyShopsByCity(@PathVariable Integer companyId, @PathVariable String city) {
        return shopService.findCompanyShopsInCity(companyId, city);
    }

    @GetMapping(GET_COMPANY_SHOP_FROM_CITY_BY_ID)
    public ShopDto getCompanyShopFromCityById(@PathVariable Integer companyId, @PathVariable String city, @PathVariable Integer shopId) {
        return shopService.findCompanyShopInCityById(companyId, city, shopId);
    }

    /**
     * получение магазинов компаний , у которых есть продукция фирмы
     */
    @GetMapping(GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS)
    public List<ShopDto> getShopsInCompanyWithFirmProducts(@PathVariable Integer firmId, @PathVariable Integer companyId) {
        return shopService.findShopsInCompanyWithFirmProducts(firmId, companyId);
    }

    /**
     * получение магазинов компаний , у которых есть продукция фирмы
     */
    @GetMapping(GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS_BY_ID)
    public ShopDto getShopsInCompanyWithFirmProductsById(@PathVariable Integer firmId, @PathVariable Integer companyId, @PathVariable Integer shopId) {
        return shopService.findShopInCompanyWithFirmProductsById(firmId, companyId, shopId);
    }

}