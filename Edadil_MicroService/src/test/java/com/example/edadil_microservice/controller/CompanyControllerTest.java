package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.service.company.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.СompanyEndpoints.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllCompany() throws Exception {

        CompanyDto companyDto = CompanyDto.builder().id(1L).companyName("Test Company").build();
        String companyDtoJson = objectMapper.writeValueAsString(List.of(companyDto));
        Mockito.when(companyService.findAllCompanies()).thenReturn(List.of(companyDto));

        mockMvc.perform(get(GET_ALL_COMPANIES))
                .andExpect(status().isOk())
                .andExpect(content().json(companyDtoJson));

    }

    @Test
    void getCompanyById() throws Exception {

        CompanyDto companyDto = CompanyDto.builder().id(1L).companyName("Test Company").build();
        String companyDtoJson = objectMapper.writeValueAsString(companyDto);


        Mockito.when(companyService.findCompanyById(1L)).thenReturn(companyDto);

        mockMvc.perform(get(GET_COMPANY_BY_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(companyDtoJson));
    }

    @Test
    void getCompaniesHavingFirmProducts() throws Exception {
        Mockito.when(companyService.findCompaniesSellingFirmProducts(1L)).thenThrow(new EntityNotFoundException("No companies found"));

        mockMvc.perform(get(GET_COMPANIES_HAVING_FIRM_PRODUCTS, 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCompaniesHavingFirmProducts_NotFound() throws Exception {
        Mockito.when(companyService.findCompaniesSellingFirmProducts(1L)).thenThrow(new EntityNotFoundException("No companies found"));
        mockMvc.perform(get(GET_COMPANIES_HAVING_FIRM_PRODUCTS, 1))
                .andExpect(status().isNotFound());
    }


    @Test
    void getCompaniesHavingFirmProductsById() throws Exception {

        CompanyDto companyDto = CompanyDto.builder().id(1L).companyName("Test Company").build();
        String companyDtoJson = objectMapper.writeValueAsString(companyDto);


        Mockito.when(companyService.findCompanySellingFirmProductsById(1L, 1L)).thenReturn(companyDto);

        mockMvc.perform(get(GET_COMPANIES_HAVING_FIRM_PRODUCTS_BY_ID, 1, 1))
                .andExpect(status().isOk())
                .andExpect(content().json(companyDtoJson));
    }
}