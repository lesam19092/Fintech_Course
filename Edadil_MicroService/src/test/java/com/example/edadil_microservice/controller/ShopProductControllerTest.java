package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.model.consts.endpoints.ShopProductEndpoints;
import com.example.edadil_microservice.service.shop_product.ShopProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ShopProductEndpoints.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopProductController.class)
class ShopProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopProductService shopProductService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getCompanyShopProducts() throws Exception {
        ShopProductDto shopProductDto = ShopProductDto.builder().build();
        String json = objectMapper.writeValueAsString(shopProductDto);

        when(shopProductService.retrieveShopProducts(1L, "City", 1L)).thenReturn(shopProductDto);

        mockMvc.perform(get(GET_COMPANY_SHOP_PRODUCTS, 1L, "City", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void getShopsWithProduct() throws Exception {
        ShopProductDto shopProductDto = ShopProductDto.builder().build();
        List<ShopProductDto> shopProductDtos = List.of(shopProductDto);
        String json = objectMapper.writeValueAsString(shopProductDtos);

        when(shopProductService.findShopsSellingProduct(1L, 1L)).thenReturn(shopProductDtos);

        mockMvc.perform(get(GET_SHOPS_WITH_PRODUCT, 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void findFirmProductsInShop() throws Exception {
        ShopProductDto shopProductDto = ShopProductDto.builder().build();
        String json = objectMapper.writeValueAsString(shopProductDto);

        when(shopProductService.findProductsInShopByFirmAndCompany(1L, 1L, 1L)).thenReturn(shopProductDto);

        mockMvc.perform(get(GET_FIRM_PRODUCTS_IN_SHOP, 1L, 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}