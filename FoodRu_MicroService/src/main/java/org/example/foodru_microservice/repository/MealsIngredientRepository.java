package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.MealsIngredient;
import org.example.foodru_microservice.model.entity.MealsIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealsIngredientRepository extends JpaRepository<MealsIngredient, MealsIngredientId> {
}