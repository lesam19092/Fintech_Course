package org.example.foodru_microservice.service.user;

import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.model.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(String jwtToken);

    boolean addMeal(Long id, String username);

    User getUserByName(String username);

    List<MealDto> getAllMeals(String username);

}
