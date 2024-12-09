package org.example.foodru_microservice.service.menu_meals;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Menu;
import org.example.foodru_microservice.model.entity.MenuMealId;
import org.example.foodru_microservice.repository.MealRepository;
import org.example.foodru_microservice.repository.MenuMealRepository;
import org.example.foodru_microservice.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuMealServiceImplTest extends IntegrationTestBase {

    @Autowired
    private MenuMealService menuMealService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MenuMealRepository menuMealRepository;

    @Test
    void addMealToMenu_ShouldAddMealToMenu() {
        Menu menu = menuRepository.findById(1L).orElseThrow();
        Meal meal = mealRepository.findById(2L).orElseThrow();

        menuMealService.addMealToMenu(menu, meal);

        boolean exists = menuMealRepository.existsById(new MenuMealId(menu.getId(), meal.getId()));
        assertTrue(exists, "Meal should be added to menu");
    }
}
