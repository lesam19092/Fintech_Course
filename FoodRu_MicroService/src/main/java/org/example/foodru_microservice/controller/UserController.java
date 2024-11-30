package org.example.foodru_microservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.model.consts.endpoints.UserEndPoints;
import org.example.foodru_microservice.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(UserEndPoints.ADD_MEAL)
    public ResponseEntity<String> addMeal(@PathVariable Long mealId, Principal principal) {
        boolean added = userService.addMeal(mealId, principal.getName());
        return ResponseEntity.ok(added ? "Meal added" : "Meal was already added");
    }

    @PostMapping(UserEndPoints.ADD_MEAL_TO_MENU)
    public ResponseEntity<String> addMealToMenu(@PathVariable Long mealId, @PathVariable String menuName, Principal principal) {
        boolean added = userService.addMealToMenu(mealId, menuName, principal.getName());
        return ResponseEntity.ok(added ? "Meal added to menu" : "Meal was already in the menu");
    }


    @PostMapping(UserEndPoints.CREATE_MENU)
    public ResponseEntity<String> createMenu(Principal principal, @PathVariable String menuName) {
        boolean created = userService.createMenu(principal.getName(), menuName);
        return ResponseEntity.ok(created ? "Menu created" : "Menu was already created");
    }

    @GetMapping(UserEndPoints.USER_MEALS)
    public List<MealDto> getAllMeals(Principal principal) {
        return userService.getAllMeals(principal.getName());
    }


}
