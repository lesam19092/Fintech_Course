package org.example.foodru_microservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.api.UserApi;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.model.consts.endpoints.UserEndPoints;
import org.example.foodru_microservice.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController implements UserApi {

    private final UserService userService;


    @PostMapping(UserEndPoints.ADD_MEAL)
    public String addMeal(@PathVariable Long mealId, Principal principal) {
        userService.addMeal(mealId, principal.getName());
        return "Meal added";
    }

    @PostMapping(UserEndPoints.ADD_MEAL_TO_MENU)
    public String addMealToMenu(@PathVariable Long mealId, @PathVariable String menuName, Principal principal) {
        userService.addMealToMenu(mealId, menuName, principal.getName());
        return "Meal added to menu";
    }

    @PostMapping(UserEndPoints.CREATE_MENU)
    public String createMenu(Principal principal, @PathVariable String menuName) {
        userService.createMenu(principal.getName(), menuName);
        return "Menu created";
    }


    @GetMapping(UserEndPoints.USER_MEALS)
    public List<MealDto> getAllMeals(Principal principal) {
        return userService.getAllMeals(principal.getName());
    }


}
