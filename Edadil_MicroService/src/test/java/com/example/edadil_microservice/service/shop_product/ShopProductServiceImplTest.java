package com.example.edadil_microservice.service.shop_product;

import com.example.edadil_microservice.IntegrationTestBase;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShopProductServiceImplTest extends IntegrationTestBase {

    @Autowired
    private ShopProductService shopProductService;

    @Test
    void retrieveShopProducts() {
        Long companyId = 1L;
        String city = "Москва";
        Long shopId = 1L;

        ShopProductDto result = shopProductService.retrieveShopProducts(companyId, city, shopId);

        assertEquals("Пятерочка", result.getShop().getCompanyName());
        assertTrue(1L == result.getShop().getId());
    }

    @Test
    void retrieveShopProducts_notFound() {
        Long companyId = 999L;
        String city = "Unknown";
        Long shopId = 999L;

        assertThrows(EntityNotFoundException.class, () -> {
            shopProductService.retrieveShopProducts(companyId, city, shopId);
        });

    }

    @Test
    void findShopsSellingProduct() {
        Long firmId = 1L;
        Long productId = 1L;

        List<ShopProductDto> result = shopProductService.findShopsSellingProduct(firmId, productId);


        assertEquals(5, result.size());
        assertNotNull(result);
    }

    @Test
    void findShopsSellingProduct_notFound() {
        Long firmId = 999L;
        Long productId = 999L;

        assertThrows(EntityNotFoundException.class, () -> {
            shopProductService.findShopsSellingProduct(firmId, productId);
        });

    }

    @Test
    void findProductsInShopByFirmAndCompany() {
        Long firmId = 1L;
        Long companyId = 1L;
        Long shopId = 1L;

        ShopProductDto result = shopProductService.findProductsInShopByFirmAndCompany(firmId, companyId, shopId);

        assertEquals("Пятерочка", result.getShop().getCompanyName());
        assertEquals(2, result.getProducts().size());
    }

    @Test
    void findProductsInShopByFirmAndCompany_notFound() {
        Long firmId = 999L;
        Long companyId = 999L;
        Long shopId = 999L;

        assertThrows(EntityNotFoundException.class, () -> {
            shopProductService.findProductsInShopByFirmAndCompany(firmId, companyId, shopId);
        });


    }

    @Test
    void getAllShopsWithProducts() {
        List<ShopProductDto> result = shopProductService.getAllShopsWithProducts();
        System.out.println(result.size());

        assertNotNull(result);
    }
}