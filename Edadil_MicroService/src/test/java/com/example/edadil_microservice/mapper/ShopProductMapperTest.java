package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.ProductDetailsDto;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.model.entity.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ShopProductMapperTest {

    private final ShopMapper shopMapper = new ShopMapper();
    private final ShopProductMapper shopProductMapper = new ShopProductMapper(shopMapper);

    @Test
    void toProductDto_ShouldConvertShopProductToProductDetailsDto() {
        Firm firm = new Firm();
        firm.setFirmName("Test Firm");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setFirm(firm);

        Shop shop = new Shop();
        shop.setId(1L);

        ShopProductId shopProductId = new ShopProductId();

        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(shopProductId);
        shopProduct.setShop(shop);
        shopProduct.setProduct(product);
        shopProduct.setCount(10);
        shopProduct.setPrice(99.99);

        ProductDetailsDto productDetailsDto = shopProductMapper.toProductDto(shopProduct);

        assertAll(
                "Проверка конвертации ShopProduct в ProductDetailsDto",
                () -> assertThat(productDetailsDto).isNotNull(),
                () -> assertThat(productDetailsDto.getName()).isEqualTo("Test Product"),
                () -> assertThat(productDetailsDto.getFirm()).isEqualTo("Test Firm"),
                () -> assertThat(productDetailsDto.getCount()).isEqualTo(10),
                () -> assertThat(productDetailsDto.getPrice()).isEqualTo(99.99)
        );
    }

    @Test
    void toShopProductDto_ShouldConvertSingleShopProductToShopProductDto() {
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setCity("Test City");
        shop.setAddress("Test Address");
        Company company = new Company();
        company.setCompanyName("Test Company");
        shop.setNameOfCompany(company);

        Firm firm = new Firm();
        firm.setFirmName("Test Firm");

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setFirm(firm);

        ShopProductId shopProductId = new ShopProductId();

        ShopProduct shopProduct = new ShopProduct();
        shopProduct.setId(shopProductId);
        shopProduct.setShop(shop);
        shopProduct.setProduct(product);
        shopProduct.setCount(10);
        shopProduct.setPrice(99.99);

        ShopProductDto shopProductDto = shopProductMapper.toShopProductDto(shopProduct);
        assertAll(
                "Проверка конвертации одного ShopProduct в ShopProductDto",
                () -> assertThat(shopProductDto).isNotNull(),
                () -> assertThat(shopProductDto.getShop()).isNotNull(),
                () -> assertThat(shopProductDto.getShop().getId()).isEqualTo(1L),
                () -> assertThat(shopProductDto.getProducts()).hasSize(1),
                () -> assertThat(shopProductDto.getProducts().get(0).getName()).isEqualTo("Test Product")
        );
    }

    @Test
    void toShopProductDto_ShouldConvertListOfShopProductsToShopProductDto() {
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setCity("Test City");
        shop.setAddress("Test Address");
        Company company = new Company();
        company.setCompanyName("Test Company");
        shop.setNameOfCompany(company);

        Firm firm = new Firm();
        firm.setFirmName("Test Firm");

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setFirm(firm);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setFirm(firm);

        ShopProductId shopProductId1 = new ShopProductId();
        ShopProduct shopProduct1 = new ShopProduct();
        shopProduct1.setId(shopProductId1);
        shopProduct1.setShop(shop);
        shopProduct1.setProduct(product1);
        shopProduct1.setCount(5);
        shopProduct1.setPrice(49.99);

        ShopProductId shopProductId2 = new ShopProductId();
        ShopProduct shopProduct2 = new ShopProduct();
        shopProduct2.setId(shopProductId2);
        shopProduct2.setShop(shop);
        shopProduct2.setProduct(product2);
        shopProduct2.setCount(3);
        shopProduct2.setPrice(29.99);

        List<ShopProduct> shopProducts = List.of(shopProduct1, shopProduct2);

        ShopProductDto shopProductDto = shopProductMapper.toShopProductDto(shopProducts);

        assertAll(
                "Проверка конвертации списка ShopProduct в ShopProductDto",
                () -> assertThat(shopProductDto).isNotNull(),
                () -> assertThat(shopProductDto.getShop()).isNotNull(),
                () -> assertThat(shopProductDto.getShop().getId()).isEqualTo(1L),
                () -> assertThat(shopProductDto.getProducts()).hasSize(2)
        );
    }
}
