package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {
}