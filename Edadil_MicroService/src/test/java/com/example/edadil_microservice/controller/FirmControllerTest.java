package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.service.firm.FirmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.FirmEndpoints.GET_ALL_FIRMS;
import static com.example.edadil_microservice.model.consts.endpoints.FirmEndpoints.GET_FIRM_BY_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FirmController.class)
class FirmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirmService firmService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllFirms() throws Exception {

        FirmDto firmDto1 = FirmDto.builder().id(1L).firmName("Firm1").build();
        FirmDto firmDto2 = FirmDto.builder().id(2L).firmName("Firm2").build();

        String json = objectMapper.writeValueAsString(List.of(firmDto1, firmDto2));

        when(firmService.findAllFirms()).thenReturn(List.of(firmDto1, firmDto2));
        mockMvc.perform(get(GET_ALL_FIRMS))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

    }

    @Test
    void getFirmById() throws Exception {

        FirmDto firmDto1 = FirmDto.builder().id(1L).firmName("Firm1").build();

        String json = objectMapper.writeValueAsString(firmDto1);

        when(firmService.findFirmById(1L)).thenReturn(firmDto1);


        mockMvc.perform(get(GET_FIRM_BY_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

    }

    @Test
    void getFirmById_notFound() throws Exception {
        when(firmService.findFirmById(999L)).thenThrow(new EntityNotFoundException("Firm not found"));

        mockMvc.perform(get(GET_FIRM_BY_ID, 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFirms_empty() throws Exception {
        when(firmService.findAllFirms()).thenReturn(List.of());

        mockMvc.perform(get(GET_ALL_FIRMS))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}