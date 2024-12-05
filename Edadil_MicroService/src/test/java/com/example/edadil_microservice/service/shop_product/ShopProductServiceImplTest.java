package com.example.edadil_microservice.service.shop_product;

import com.example.edadil_microservice.BaseTestContainer;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
class ShopProductServiceImplTest extends BaseTestContainer {

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
    void findShopsSellingProduct() {
        Long firmId = 1L;
        Long productId = 1L;

        List<ShopProductDto> result = shopProductService.findShopsSellingProduct(firmId, productId);


        assertEquals(5, result.size());
        assertNotNull(result);
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
    void getAllShopsWithProducts() {
        List<ShopProductDto> result = shopProductService.getAllShopsWithProducts();
        System.out.println(result.size());

        assertNotNull(result);
    }
}