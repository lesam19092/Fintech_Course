package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.service.shop.ShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ShopEndpoints.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ShopController.class)
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void getCompanyShops() throws Exception {
        List<ShopDto> shopDtos = List.of(
                ShopDto.builder().id(1L).companyName("Shop1").build(),
                ShopDto.builder().id(2L).companyName("Shop2").build()
        );

        String json = objectMapper.writeValueAsString(shopDtos);

        when(shopService.findCompanyShops(1L)).thenReturn(shopDtos);

        mockMvc.perform(get(GET_COMPANY_SHOPS, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void getCompanyShopsByCity() throws Exception {
        List<ShopDto> shopDtos = List.of(
                ShopDto.builder().id(1L).companyName("Shop1").build(),
                ShopDto.builder().id(2L).companyName("Shop2").build()
        );

        String json = objectMapper.writeValueAsString(shopDtos);

        when(shopService.findCompanyShopsInCity(1L, "City1")).thenReturn(shopDtos);

        mockMvc.perform(get(GET_COMPANY_SHOPS_BY_CITY, 1L, "City1"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void getCompanyShopFromCityById() throws Exception {
        ShopDto shopDto = ShopDto.builder().id(1L).companyName("Shop1").build();

        String json = objectMapper.writeValueAsString(shopDto);

        when(shopService.findCompanyShopInCityById(1L, "City1", 1L)).thenReturn(shopDto);

        mockMvc.perform(get(GET_COMPANY_SHOP_FROM_CITY_BY_ID, 1L, "City1", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void getShopsInCompanyWithFirmProducts() throws Exception {
        List<ShopDto> shopDtos = List.of(
                ShopDto.builder().id(1L).companyName("Shop1").build(),
                ShopDto.builder().id(2L).companyName("Shop2").build()
        );

        String json = objectMapper.writeValueAsString(shopDtos);

        when(shopService.findShopsInCompanyWithFirmProducts(1L, 1L)).thenReturn(shopDtos);

        mockMvc.perform(get(GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS, 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void getShopsInCompanyWithFirmProductsById() throws Exception {
        ShopDto shopDto = ShopDto.builder().id(1L).companyName("Shop1").build();

        String json = objectMapper.writeValueAsString(shopDto);

        when(shopService.findShopInCompanyWithFirmProductsById(1L, 1L, 1L)).thenReturn(shopDto);

        mockMvc.perform(get(GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS_BY_ID, 1L, 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
