package org.example.foodru_microservice.mapper;

import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.model.entity.Ingredient;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.service.kafka.dto.IngredientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MealMapperTest {

    @InjectMocks
    private MealMapper mealMapper;
    @Mock
    private IngredientMapper ingredientMapper;

    @Test
    void toDto_shouldMapMealToMealDto() {
        Meal meal = new Meal();
        meal.setId(1L);
        meal.setName("Test Meal");
        meal.setCookInstructions("Test Instructions");

        MealDto mealDto = mealMapper.toDto(meal);

        assertThat(mealDto).isNotNull();
        assertThat(mealDto.getId()).isEqualTo(1L);
        assertThat(mealDto.getName()).isEqualTo("Test Meal");
        assertThat(mealDto.getCookInstructions()).isEqualTo("Test Instructions");
    }

    @Test
    void toDtoWithIngredients_shouldMapMealToMealWithIngredientDto() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setId(1L);

        Meal meal = new Meal();
        meal.setId(1L);
        meal.setName("Test Meal");
        meal.setCookInstructions("Test Instructions");


        IngredientDto ingredientDto = IngredientDto.builder()
                .name("Test Ingredient")
                .build();

        Mockito.when(ingredientMapper.toDtoList(meal.getMealsIngredients()))
                .thenReturn(List.of(ingredientDto));

        MealWithIngredientDto mealWithIngredientDto = mealMapper.toDtoWithIngredients(meal);

        assertThat(mealWithIngredientDto.getName()).isEqualTo("Test Meal");
        assertThat(mealWithIngredientDto.getCookInstructions()).isEqualTo("Test Instructions");
        assertThat(mealWithIngredientDto.getIngredients()).hasSize(1);
        assertThat(mealWithIngredientDto.getIngredients().get(0).getName()).isEqualTo("Test Ingredient");
    }
}
