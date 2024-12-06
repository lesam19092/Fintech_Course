package org.example.foodru_microservice.mapper;

import org.example.foodru_microservice.model.entity.Ingredient;
import org.example.foodru_microservice.model.entity.MealsIngredient;
import org.example.foodru_microservice.service.kafka.dto.IngredientDto;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientMapperTest {

    @Test
    void toDto() {
        MealsIngredient ingredient = new MealsIngredient();
        ingredient.setIngredient(new Ingredient());
        ingredient.getIngredient().setName("Tomato");
        ingredient.setMeasure(1.0);
        IngredientMapper mapper = new IngredientMapper();
        IngredientDto dto = mapper.toDto(ingredient);

        assertEquals("Tomato", dto.getName());
        assertEquals(1.0, dto.getMeasure());
    }

    @Test
    void toDtoList() {
        MealsIngredient ingredient1 = new MealsIngredient();
        ingredient1.setMeasure(1.0);
        ingredient1.setIngredient(new Ingredient());
        ingredient1.getIngredient().setName("Tomato");

        MealsIngredient ingredient2 = new MealsIngredient();
        ingredient2.setIngredient(new Ingredient());
        ingredient2.getIngredient().setName("Onion");
        ingredient2.setMeasure(0.5);
        Set<MealsIngredient> ingredients = new LinkedHashSet<>(List.of(ingredient1, ingredient2));
        IngredientMapper mapper = new IngredientMapper();
        List<IngredientDto> dtoList = mapper.toDtoList(ingredients);

        assertEquals(2, dtoList.size());
        assertEquals("Tomato", dtoList.get(0).getName());
        assertEquals(1.0, dtoList.get(0).getMeasure());
        assertEquals("Onion", dtoList.get(1).getName());
        assertEquals(0.5, dtoList.get(1).getMeasure());
    }
}