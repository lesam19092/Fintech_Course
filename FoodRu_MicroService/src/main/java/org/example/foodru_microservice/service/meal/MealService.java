package org.example.foodru_microservice.service.meal;

import org.example.foodru_microservice.model.dto.MealDto;
import org.example.foodru_microservice.model.dto.MealWithIngredientDto;

import java.util.List;

public interface MealService {


    List<MealDto> getAllMeals();

    MealDto getMealById(Integer id);

    MealWithIngredientDto getMealsIngredients(Integer id);

    void getCheapestMealsIngredients(Integer id);
}
