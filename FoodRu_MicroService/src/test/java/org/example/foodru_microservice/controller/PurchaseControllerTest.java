package org.example.foodru_microservice.controller;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.model.consts.endpoints.PurchaseEndPoints;
import org.example.foodru_microservice.service.purchase.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest extends IntegrationTestBase {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;


    @Test
    @WithMockUser
    void buyCheapestMealIngredients() throws Exception {
        doNothing().when(purchaseService).buyCheapestMealsIngredients(1L, "testUser");
        mockMvc.perform(get(PurchaseEndPoints.BUY_MEAL, 1L, "testUser"))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment was successful"));

    }

    @Test
    void buyCheapestMealIngredients_withException() throws Exception {
        doNothing().when(purchaseService).buyCheapestMealsIngredients(1L, "testUser");
        mockMvc.perform(get(PurchaseEndPoints.BUY_MEAL, 1L, "testUser"))
                .andExpect(status().isNotFound());
    }


}