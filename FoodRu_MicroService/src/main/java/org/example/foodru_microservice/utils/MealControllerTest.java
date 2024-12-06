/*
package org.example.foodru_microservice.controller;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.model.consts.endpoints.MealEndPoints;
import org.example.foodru_microservice.service.jwt.JwtTokenService;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.example.foodru_microservice.service.meal.MealService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MealController.class)
class MealControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private MealService mealService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getMeals() throws Exception {
        MealDto mealDto = MealDto.builder().id(1L).name("Test Meal").build();
        String mealDtoJson = objectMapper.writeValueAsString(List.of(mealDto));
        Mockito.when(mealService.getAllMeals()).thenReturn(List.of(mealDto));

        mockMvc.perform(get(MealEndPoints.MEALS))
                .andExpect(status().isOk())
                .andExpect(content().json(mealDtoJson));
    }

    @Test
    void getMealById() throws Exception {
        MealDto mealDto = MealDto.builder().id(1L).name("Test Meal").build();
        String mealDtoJson = objectMapper.writeValueAsString(mealDto);
        Mockito.when(mealService.getMealDtoById(1L)).thenReturn(mealDto);

        mockMvc.perform(get(MealEndPoints.MEAL, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mealDtoJson));
    }

    @Test
    void getMealIngredients() throws Exception {
        MealWithIngredientDto mealWithIngredientDto = MealWithIngredientDto.builder()
                .id(1L)
                .ingredients(List.of())
                .build();
        String mealWithIngredientJson = objectMapper.writeValueAsString(mealWithIngredientDto);

        Mockito.when(mealService.getMealsIngredients(1L)).thenReturn(mealWithIngredientDto);

        mockMvc.perform(get(MealEndPoints.MEAL_INGREDIENTS, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mealWithIngredientJson));
    }

    @Test
    @WithMockUser
    void getCheapestMealIngredients() throws Exception {
        PaymentReceiptResponse receiptResponse = new PaymentReceiptResponse();
        receiptResponse.setCost(9.99);
        receiptResponse.setCompanyName("Cheapest Meal");

        String receiptResponseJson = objectMapper.writeValueAsString(receiptResponse);

        Mockito.when(mealService.getCheapestMealsIngredients(1L)).thenReturn(receiptResponse);

        mockMvc.perform(get(MealEndPoints.MEAL_CHEAPEST, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(receiptResponseJson));
    }

    @Test
    void getCheapestMealIngredients_Unauthorized() throws Exception {
        mockMvc.perform(get(MealEndPoints.MEAL_CHEAPEST, 1L))
                .andExpect(status().isUnauthorized());
    }
}
*/
