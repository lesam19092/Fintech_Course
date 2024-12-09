package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.model.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductMapperTest {

    private ProductMapper productMapper = new ProductMapper();

    @Test
    void toDto_ShouldConvertProductToProductDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        ProductDto productDto = productMapper.toDto(product);

        assertAll(
                "Проверка конвертации Product в ProductDto",
                () -> assertThat(productDto).isNotNull(),
                () -> assertThat(productDto.getId()).isEqualTo(1L),
                () -> assertThat(productDto.getName()).isEqualTo("Test Product")
        );
    }

    @Test
    void toDtoList_ShouldConvertListOfProductsToListOfProductDtos() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");

        List<Product> products = Arrays.asList(product1, product2);

        List<ProductDto> productDtos = productMapper.toDtoList(products);
        assertAll(
                "Проверка конвертации списка Product в список ProductDto",
                () -> assertThat(productDtos).isNotNull(),
                () -> assertThat(productDtos).hasSize(2),
                () -> assertThat(productDtos.get(0).getId()).isEqualTo(1L),
                () -> assertThat(productDtos.get(1).getId()).isEqualTo(2L)
                );
    }

    @Test
    void toDtoList_ShouldReturnEmptyListWhenInputIsEmpty() {
        List<Product> products = List.of();
        List<ProductDto> productDtos = productMapper.toDtoList(products);
        assertAll(
                "Проверка конвертации пустого списка Product в пустой список ProductDto",
                () -> assertThat(productDtos).isNotNull(),
                () -> assertThat(productDtos).isEmpty()
        );
    }
}
