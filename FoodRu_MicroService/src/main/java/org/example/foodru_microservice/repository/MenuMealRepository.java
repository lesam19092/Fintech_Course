package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.MenuMeal;
import org.example.foodru_microservice.model.MenuMealId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuMealRepository extends JpaRepository<MenuMeal, MenuMealId> {
}