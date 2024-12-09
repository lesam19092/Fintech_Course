package org.example.foodru_microservice.service.menu;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.handler.exception.MealAlreadyExistsException;
import org.example.foodru_microservice.handler.exception.MenuAlreadyExistsException;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceImplTest extends IntegrationTestBase {

    @Autowired
    private MenuServiceImpl menuService;

    @Test
    void createMenu_Success() {
        User user = new User();
        user.setId(3L);
        user.setName("Bob Brown");

        menuService.createMenu(user, "Dinner Menu");

        List<MenuDto> menus = menuService.getMenusByUsername("Bob Brown");
        assertEquals(1, menus.size());
        assertEquals("Dinner Menu", menus.get(0).getName());
    }

    @Test
    void createMenu_AlreadyExists() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        assertThrows(MenuAlreadyExistsException.class, () -> menuService.createMenu(user, "Breakfast Menu"));
    }

    @Test
    void getMenusByUsername_Success() {
        List<MenuDto> menus = menuService.getMenusByUsername("John Doe");
        assertEquals(1, menus.size());
        assertEquals("Breakfast Menu", menus.get(0).getName());
    }

    @Test
    void getMenusByUsername_NotFound() {
        assertThrows(EntitySearchException.class, () -> menuService.getMenusByUsername("Nonexistent User"));
    }

    @Test
    void getMealsByMenuId_Success() {
        List<MealDto> meals = menuService.getMealsByMenuId(1L);
        assertEquals(1, meals.size());
        assertEquals("Pancakes", meals.get(0).getName());
    }

    @Test
    void getMealsByMenuId_NotFound() {
        assertThrows(EntitySearchException.class, () -> menuService.getMealsByMenuId(999L));
    }

    @Test
    void addMealToMenu_Success() {
        User user = new User();
        user.setId(2L);
        user.setName("Jane Smith");

        Meal meal = new Meal();
        meal.setId(3L);
        meal.setName("Omelette");

        menuService.addMealToMenu(meal, "Lunch Menu", user);

        List<MealDto> meals = menuService.getMealsByMenuId(2L);
        assertEquals(2, meals.size());
        assertTrue(meals.stream().anyMatch(m -> m.getName().equals("Omelette")));
    }

    @Test
    void addMealToMenu_AlreadyExists() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        Meal meal = new Meal();
        meal.setId(1L);
        meal.setName("Pancakes");

        assertThrows(MealAlreadyExistsException.class, () -> menuService.addMealToMenu(meal, "Breakfast Menu", user));
    }

    @Test
    void addMealToMenu_MenuNotFound() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        Meal meal = new Meal();
        meal.setId(1L);
        meal.setName("Pancakes");

        assertThrows(EntitySearchException.class, () -> menuService.addMealToMenu(meal, "Nonexistent Menu", user));
    }

    @Test
    void userHasMenu_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        assertTrue(menuService.userHasMenu(user, "Breakfast Menu"));
    }

    @Test
    void userHasMenu_NotFound() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        assertFalse(menuService.userHasMenu(user, "Dinner Menu"));
    }
}
