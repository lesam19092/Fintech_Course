package org.example.foodru_microservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.model.consts.endpoints.MealEndPoints;
import org.example.foodru_microservice.service.meal.MealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealController {


    private final MealService mealService;


    @GetMapping(MealEndPoints.MEALS)
    public List<MealDto> getMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping(MealEndPoints.MEAL)
    public MealDto getMealById(@PathVariable Long id) {
        return mealService.getMealDtoById(id);
    }

    @GetMapping(MealEndPoints.MEAL_INGREDIENTS)
    public MealWithIngredientDto getMealIngredients(@PathVariable Long id) {
        return mealService.getMealsIngredients(id);
    }


    @GetMapping(MealEndPoints.MEAL_CHEAPEST)
    public MealWithIngredientDto getCheapestMealIngredients(@PathVariable Long id) {
        mealService.getCheapestMealsIngredients(id);
        return null;
    }


}
