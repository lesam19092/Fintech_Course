package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.model.consts.endpoints.ProductsEndpoint;
import com.example.edadil_microservice.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getFirmProducts() throws Exception {
        ProductDto productDto1 = ProductDto.builder().id(1L).name("Product1").build();
        ProductDto productDto2 = ProductDto.builder().id(2L).name("Product2").build();

        String json = objectMapper.writeValueAsString(List.of(productDto1, productDto2));

        when(productService.findProductsByFirmId(1L)).thenReturn(List.of(productDto1, productDto2));
        mockMvc.perform(get(ProductsEndpoint.GET_FIRM_PRODUCTS, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void getFirmProductById() throws Exception {
        ProductDto productDto = ProductDto.builder().id(1L).name("Product1").build();

        String json = objectMapper.writeValueAsString(productDto);

        when(productService.findProductByIdAndFirmId(1L, 1L)).thenReturn(productDto);

        mockMvc.perform(get(ProductsEndpoint.GET_FIRM_PRODUCT_BY_ID, 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void getFirmProductById_notFound() throws Exception {
        when(productService.findProductByIdAndFirmId(1L, 999L)).thenThrow(new EntityNotFoundException("Product not found"));

        mockMvc.perform(get(ProductsEndpoint.GET_FIRM_PRODUCT_BY_ID, 1L, 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFirmProducts_empty() throws Exception {
        when(productService.findProductsByFirmId(1L)).thenReturn(List.of());

        mockMvc.perform(get(ProductsEndpoint.GET_FIRM_PRODUCTS, 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}
