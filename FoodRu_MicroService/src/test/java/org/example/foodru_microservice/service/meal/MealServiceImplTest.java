package org.example.foodru_microservice.service.meal;

import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.service.kafka.KafkaConsumer;
import org.example.foodru_microservice.service.kafka.KafkaProducer;
import org.example.foodru_microservice.service.kafka.dto.ListIngredientDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealServiceImplTest extends IntegrationTestBase {


    @Autowired
    private MealServiceImpl mealService;


    @Mock
    private MealServiceImpl mockMealService;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private KafkaConsumer kafkaConsumer;

    @Test
    void getCheapestMealsIngredients_NotFound() {
        Long invalidMealId = 999L;
        when(mockMealService.getMealsIngredients(invalidMealId)).thenThrow(EntitySearchException.class);
        assertThrows(EntitySearchException.class, () -> mealService.getCheapestMealsIngredients(invalidMealId));
        verify(kafkaProducer, times(0)).sendMessage(any(ListIngredientDto.class));
        verify(kafkaConsumer, times(0)).getResponse();
    }

    @Test
    void getAllMeals() {

        List<MealDto> meals = mealService.getAllMeals();
        assertEquals(3, meals.size());
        assertEquals("Pancakes", meals.get(0).getName());
        assertEquals("Salad", meals.get(1).getName());
        assertEquals("Omelette", meals.get(2).getName());

    }

    @Test
    void getMealDtoById() {
        MealDto meal = mealService.getMealDtoById(1L);
        assertEquals(1L, meal.getId());
        assertEquals("Pancakes", meal.getName());
        assertEquals("Mix ingredients and fry on a pan", meal.getCookInstructions());
    }

    @Test
    void getMealById() {
        Meal meal = mealService.getMealById(1L);

        assertNotNull(meal);
        assertEquals(1L, meal.getId());
        assertEquals("Pancakes", meal.getName());
    }

    @Test
    void getMealById_NotFound() {
        assertThrows(EntitySearchException.class, () -> mealService.getMealById(999L));
    }

    @Test
    void getMealsIngredients() {
        MealWithIngredientDto mealWithIngredientDto = mealService.getMealsIngredients(1L);

        assertNotNull(mealWithIngredientDto);
        assertEquals(1L, mealWithIngredientDto.getId());
        assertFalse(mealWithIngredientDto.getIngredients().isEmpty());
    }

    @Test
    void getMealsIngredients_NotFound() {
        assertThrows(EntitySearchException.class, () -> mealService.getMealsIngredients(999L));
    }

    @Test
    void getCachedResponse_ThrowsException() {
        assertThrows(EntitySearchException.class, () -> mealService.getCachedResponse(1000L));
    }

    @Test
    void getCachedResponse_NotFound() {
        assertThrows(EntitySearchException.class, () -> mealService.getCachedResponse(999L));
    }


    @Test
    void getMealDtoById_NotFound() {
        assertThrows(EntitySearchException.class, () -> mealService.getMealDtoById(999L));
    }


}