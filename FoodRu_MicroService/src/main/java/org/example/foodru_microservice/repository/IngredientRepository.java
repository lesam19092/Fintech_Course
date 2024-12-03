package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}