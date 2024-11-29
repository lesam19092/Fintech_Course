package org.example.foodru_microservice.service.meal;

import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.model.entity.Meal;

import java.util.List;

public interface MealService {


    List<MealDto> getAllMeals();

    MealDto getMealDtoById(Long id);

    Meal getMealById(Long id);

    MealWithIngredientDto getMealsIngredients(Long id);

    void getCheapestMealsIngredients(Long id);
}
