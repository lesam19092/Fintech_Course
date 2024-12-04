package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal,Long> {


    @Query("SELECT m FROM Meal m JOIN FETCH m.mealsIngredients mi JOIN FETCH mi.ingredient WHERE m.id = :id")
    Optional<Meal> getMealWithIngredients(Long id);
}