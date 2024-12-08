package com.example.edadil_microservice.service.product;

import com.example.edadil_microservice.IntegrationTestBase;
import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class ProductServiceImplTest extends IntegrationTestBase {

    @Autowired
    private ProductServiceImpl productService;


    @Test
    public void findProductsByFirmId() {
        Long firmId = 1L;
        List<ProductDto> result = productService.findProductsByFirmId(firmId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Молоко", result.get(0).getName());
        assertEquals("Кефир", result.get(1).getName());
    }

    @Test
    public void findProductByIdAndFirmId() {
        Long firmId = 1L;
        Long productId = 1L;

        ProductDto result = productService.findProductByIdAndFirmId(firmId, productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Молоко", result.getName());
    }

    @Test
    public void findProductsByFirmId_notFound() {
        Long firmId = 999L;
        assertThrows(EntityNotFoundException.class, () -> {
            productService.findProductsByFirmId(firmId);
        });
    }

    @Test
    public void findProductByIdAndFirmId_notFound() {
        Long firmId = 1L;
        Long productId = 999L;
        assertThrows(EntityNotFoundException.class, () -> {
            productService.findProductByIdAndFirmId(firmId, productId);
        });
    }
}