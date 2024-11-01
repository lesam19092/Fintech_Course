package org.example.foodru_microservice.mapper;


import org.example.foodru_microservice.model.dto.IngredientDto;
import org.example.foodru_microservice.model.entity.MealsIngredient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {

    public IngredientDto toDto(MealsIngredient ingredient) {
        return IngredientDto.builder()
                .name(ingredient.getIngredient().getName())
                .measure(ingredient.getMeasure())
                .build();
    }


    public List<IngredientDto> toDtoList(Set<MealsIngredient> ingredients) {
        return ingredients.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
