package org.example.foodru_microservice.service.user_meal;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.handler.exception.MealAlreadyExistsException;
import org.example.foodru_microservice.model.entity.UsersMealId;
import org.example.foodru_microservice.repository.MealRepository;
import org.example.foodru_microservice.repository.UserRepository;
import org.example.foodru_microservice.repository.UsersMealRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMealServiceImplTest extends IntegrationTestBase {

    @Autowired
    private UserMealServiceImpl userMealService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UsersMealRepository usersMealRepository;


    @Test
    void addMeal_whenThrowsException() {
        var meal = mealRepository.findById(1L).orElseThrow();
        var user = userRepository.findById(1L).orElseThrow();
        assertThrows(MealAlreadyExistsException.class, () -> userMealService.addMeal(meal, user));      //  (usersMealRepository.existsById(new UsersMealId(user.getId(), meal.getId())));
    }
    @Test
    void addMeal() {
        var meal = mealRepository.findById(2L).orElseThrow();
        var user = userRepository.findById(3L).orElseThrow();
        userMealService.addMeal(meal, user);
        assertTrue(usersMealRepository.existsById(new UsersMealId(user.getId(), meal.getId())));
    }

    @Test
    void getAllMeals() {
        var user = userRepository.findById(1L).orElseThrow();
        List<MealDto> meals = userMealService.getAllMeals(user);
        assertFalse(meals.isEmpty());
        assertEquals(3, meals.size());
        assertEquals("Pancakes", meals.get(0).getName());
        assertEquals("Salad", meals.get(1).getName());
    }

}