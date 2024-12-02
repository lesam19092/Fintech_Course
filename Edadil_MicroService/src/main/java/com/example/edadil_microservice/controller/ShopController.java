package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/companies/{companyId}/shops")
    public List<ShopDto> getCompanyShops(@PathVariable Integer companyId) {
        return shopService.findCompanyShops(companyId);
    }

    @GetMapping("/companies/{companyId}/shops/{city}")
    public List<ShopDto> getCompanyShopsByCity(@PathVariable Integer companyId, @PathVariable String city) {
        return shopService.findCompanyShopsInCity(companyId, city);
    }

    @GetMapping("/companies/{companyId}/shops/{city}/{shopId}")
    public ShopDto getCompanyShopFromCityById(@PathVariable Integer companyId, @PathVariable String city, @PathVariable Integer shopId) {
        return shopService.findCompanyShopInCityById(companyId, city, shopId);
    }

    /**
     * получение магазинов компаний , у которых есть продукция фирмы
     */
    @GetMapping("/firms/{firmId}/company/{companyId}/shops")
    public List<ShopDto> getShopsInCompanyWithFirmProducts(@PathVariable Integer firmId, @PathVariable Integer companyId) {
        return shopService.findShopsInCompanyWithFirmProducts(firmId, companyId);
    }


    /**
     * получение магазинов компаний , у которых есть продукция фирмы
     */
    @GetMapping("/firms/{firmId}/company/{companyId}/shops/{shopId}")
    public ShopDto getShopsInCompanyWithFirmProductsById(@PathVariable Integer firmId, @PathVariable Integer companyId, @PathVariable Integer shopId) {
        return shopService.findShopInCompanyWithFirmProductsById(firmId, companyId, shopId);
    }


}
