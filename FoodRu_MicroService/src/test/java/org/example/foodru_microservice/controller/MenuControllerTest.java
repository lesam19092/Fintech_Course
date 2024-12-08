package org.example.foodru_microservice.controller;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.model.consts.endpoints.MenuEndPoints;
import org.example.foodru_microservice.service.menu.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MenuControllerTest extends IntegrationTestBase {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @WithMockUser
    void getMenus() throws Exception {

        List<MenuDto> menus = new ArrayList<>();
        MenuDto menu1 = MenuDto.builder().id(1L).build();
        MenuDto menu2 = MenuDto.builder().id(2L).build();

        menus.add(menu1);
        menus.add(menu2);

        String menusJson = objectMapper.writeValueAsString(menus);

        when(menuService.getMenusByUsername("user")).thenReturn(menus);
        mockMvc.perform(get(MenuEndPoints.MENUS).principal(() -> "user"))
                .andExpect(status().isOk())
                .andExpect(content().json(menusJson));
    }

    @Test
    @WithMockUser
    void getMealsByMenuId() throws Exception {
        List<MealDto> meals = List.of(MealDto.builder().id(1L).name("Test Meal").build());
        String mealsJson = objectMapper.writeValueAsString(meals);

        when(menuService.getMealsByMenuId(1L)).thenReturn(meals);
        mockMvc.perform(get(MenuEndPoints.MENU, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mealsJson));
    }

    @Test
    void getMenus_Unauthorized() throws Exception {
        mockMvc.perform(get(MenuEndPoints.MENUS))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMealsByMenuId_NotFound() throws Exception {
        when(menuService.getMealsByMenuId(999L)).thenReturn(null);
        mockMvc.perform(get(MenuEndPoints.MENU, 999L))
                .andExpect(status().isNotFound());
    }
}