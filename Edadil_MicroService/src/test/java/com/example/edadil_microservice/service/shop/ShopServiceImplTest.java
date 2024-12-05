package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.IntegrationTestBase;
import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ShopServiceImplTest extends IntegrationTestBase {

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

        assertEquals(result.size(), 3);

    }

    @Test
    void getIdShops() {
        List<Long> result = shopService.getIdShops();
        assertEquals(result.size(), 11);
    }

    @Test
    void findCompanyShops_whenCompanyIdNotFound_throwsException() {
        Long nonExistentCompanyId = 999L;

        assertThrows(EntityNotFoundException.class,
                () -> shopService.findCompanyShops(nonExistentCompanyId));
    }

    @Test
    void findCompanyShopsInCity_whenCityNotFound_throwsException() {
        Long companyId = 1L;
        String nonExistentCity = "Невероград";

        assertThrows(EntityNotFoundException.class,
                () -> shopService.findCompanyShopsInCity(companyId, nonExistentCity),
                "Expected EntityNotFoundException for a non-existent city"
        );
    }

    @Test
    void findCompanyShopInCityById_whenShopIdNotFound_throwsException() {
        Long companyId = 1L;
        String city = "Москва";
        Long nonExistentShopId = 999L;

        assertThrows(EntityNotFoundException.class,
                () -> shopService.findCompanyShopInCityById(companyId, city, nonExistentShopId),
                "Expected EntityNotFoundException for a non-existent shop ID"
        );
    }

    @Test
    void findShopInCompanyWithFirmProductsById_whenFirmNotFound_throwsException() {
        Long nonExistentFirmId = 999L;
        Long companyId = 1L;
        Long shopId = 6L;

        assertThrows(EntityNotFoundException.class,
                () -> shopService.findShopInCompanyWithFirmProductsById(nonExistentFirmId, companyId, shopId),
                "Expected EntityNotFoundException for a non-existent firm ID"
        );
    }

    @Test
    void findShopInCompanyWithFirmProductsById_whenShopIdNotFound_throwsException() {
        Long firmId = 1L;
        Long companyId = 1L;
        Long nonExistentShopId = 999L;

        assertThrows(EntityNotFoundException.class,
                () -> shopService.findShopInCompanyWithFirmProductsById(firmId, companyId, nonExistentShopId),
                "Expected EntityNotFoundException for a non-existent shop ID in the company"
        );
    }

    @Test
    void findShopsInCompanyWithFirmProducts_whenFirmProductsNotFound_throwsException() {
        Long nonExistentFirmId = 999L;
        Long companyId = 1L;

        assertThrows(EntityNotFoundException.class,
                () -> shopService.findShopsInCompanyWithFirmProducts(nonExistentFirmId, companyId),
                "Expected EntityNotFoundException for no shops with products for the given firm"
        );
    }

}