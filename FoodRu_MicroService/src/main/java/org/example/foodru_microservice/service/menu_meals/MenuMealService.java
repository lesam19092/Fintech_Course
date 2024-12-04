package org.example.foodru_microservice.service.menu_meals;

import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Menu;

public interface MenuMealService {

    void addMealToMenu(Menu menu, Meal meal);
}
