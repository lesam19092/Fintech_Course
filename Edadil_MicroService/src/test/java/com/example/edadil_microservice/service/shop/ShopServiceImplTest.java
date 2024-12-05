package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.BaseTestContainer;
import com.example.edadil_microservice.controller.dto.ShopDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShopServiceImplTest extends BaseTestContainer {

    @Autowired
    private ShopServiceImpl shopService;

    @Test
    void findCompanyShops() {
        Long companyId = 1L;

        List<ShopDto> result = shopService.findCompanyShops(companyId);
        assertEquals(result.size(), 6);
    }

    @Test
    void findCompanyShopsInCity() {
        Long companyId = 1L;
        String city = "Москва";
        List<ShopDto> result = shopService.findCompanyShopsInCity(companyId, city);
        assertEquals(result.size(), 3);

    }

    @Test
    void findCompanyShopInCityById() {
        Long companyId = 1L;
        String city = "Москва";
        Long shopId = 5L;
        ShopDto result = shopService.findCompanyShopInCityById(companyId, city, shopId);

        assertEquals(result.getAddress(), "Московское шоссе, 123");
    }

    @Test
    void findShopInCompanyWithFirmProductsById() {
        Long firmId = 1L;
        Long companyId = 1L;
        Long shopId = 6L;
        ShopDto result = shopService.findShopInCompanyWithFirmProductsById(firmId, companyId, shopId);
        assertEquals(result.getAddress(), "Невский проспект, 87");
    }

    @Test
    void findShopsInCompanyWithFirmProducts() {
        Long firmId = 1L;
        Long companyId = 1L;

        List<ShopDto> result = shopService.findShopsInCompanyWithFirmProducts(firmId, companyId);

        System.out.println(result);

        assertEquals(result.size(),3);

    }

    @Test
    void getIdShops() {
        List<Long> result = shopService.getIdShops();
        assertEquals(result.size(), 11);
    }
}