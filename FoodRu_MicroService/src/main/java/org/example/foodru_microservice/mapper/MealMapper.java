package org.example.foodru_microservice.mapper;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.model.entity.Meal;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealMapper {

    private final IngredientMapper ingredientMapper;

    public MealDto toDto(Meal meal) {
        return MealDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .cookInstructions(meal.getCookInstructions())
                .build();
    }

    public MealWithIngredientDto toDtoWithIngredients(Meal meal) {
        return MealWithIngredientDto.builder()
                .id(meal.getId())
                .name(meal.getName())
                .cookInstructions(meal.getCookInstructions())
                .ingredients(ingredientMapper.toDtoList(meal.getMealsIngredients()))
                .build();
    }
}