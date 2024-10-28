package org.example.foodru_microservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.model.dto.MealDto;
import org.example.foodru_microservice.model.dto.MealWithIngredientDto;
import org.example.foodru_microservice.service.MealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealController {


    private final MealService mealService;


    @GetMapping("/meals")
    public List<MealDto> getMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/meals/{id}")
    public MealDto getMealById(@PathVariable Integer id) {
        return mealService.getMealById(id);
    }

    @GetMapping("/meals/{id}/ingredients")
    public MealWithIngredientDto getMealIngredients(@PathVariable Integer id) {
       return mealService.getMealsIngredients(id);
    }
}
