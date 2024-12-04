package org.example.foodru_microservice.service.user_meal;

import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.User;

import java.util.List;

public interface UserMealService {

    void addMeal(Meal meal, User user);

    List<MealDto> getAllMeals(User user);

}
