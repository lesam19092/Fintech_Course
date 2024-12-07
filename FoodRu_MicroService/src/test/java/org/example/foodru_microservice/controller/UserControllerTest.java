package org.example.foodru_microservice.controller;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.model.consts.endpoints.UserEndPoints;
import org.example.foodru_microservice.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "testUser")
    void addMeal() throws Exception {
        doNothing().when(userService).addMeal(1L, "testUser");

        mockMvc.perform(post(UserEndPoints.ADD_MEAL, 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Meal added"));

        verify(userService, times(1)).addMeal(1L, "testUser");
    }

    @Test
    @WithMockUser(username = "testUser")
    void addMealToMenu() throws Exception {
        doNothing().when(userService).addMealToMenu(1L, "TestMenu", "testUser");

        mockMvc.perform(post(UserEndPoints.ADD_MEAL_TO_MENU, 1L, "TestMenu"))
                .andExpect(status().isOk())
                .andExpect(content().string("Meal added to menu"));

        verify(userService, times(1)).addMealToMenu(1L, "TestMenu", "testUser");
    }

    @Test
    @WithMockUser(username = "testUser")
    void createMenu() throws Exception {
        doNothing().when(userService).createMenu("testUser", "NewMenu");

        mockMvc.perform(post(UserEndPoints.CREATE_MENU, "NewMenu"))
                .andExpect(status().isOk())
                .andExpect(content().string("Menu created"));

        verify(userService, times(1)).createMenu("testUser", "NewMenu");
    }

    @Test
    @WithMockUser(username = "testUser")
    void getAllMeals() throws Exception {
        List<MealDto> meals = List.of(MealDto.builder().id(1L).name("Test Meal").build());
        String mealsJson = objectMapper.writeValueAsString(meals);

        when(userService.getAllMeals("testUser")).thenReturn(meals);

        mockMvc.perform(get(UserEndPoints.USER_MEALS))
                .andExpect(status().isOk())
                .andExpect(content().json(mealsJson));

        verify(userService, times(1)).getAllMeals("testUser");
    }

    @Test
    void addMeal_Unauthorized() throws Exception {
        mockMvc.perform(post(UserEndPoints.ADD_MEAL, 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void addMealToMenu_Unauthorized() throws Exception {
        mockMvc.perform(post(UserEndPoints.ADD_MEAL_TO_MENU, 1L, "TestMenu"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMenu_Unauthorized() throws Exception {
        mockMvc.perform(post(UserEndPoints.CREATE_MENU, "NewMenu"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllMeals_Unauthorized() throws Exception {
        mockMvc.perform(get(UserEndPoints.USER_MEALS))
                .andExpect(status().isNotFound());
    }
}
