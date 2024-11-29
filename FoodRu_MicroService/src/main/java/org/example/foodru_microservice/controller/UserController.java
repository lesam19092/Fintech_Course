package org.example.foodru_microservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api-v1")
public class UserController {

    private final UserService userService;


    @GetMapping("/meals/{id}/ingredients/add")
    public ResponseEntity<String> addMeal(@PathVariable Long id, Principal principal) {
        boolean added = userService.addMeal(id, principal.getName());
        return ResponseEntity.ok(added ? "Meal added" : "Meal was already added");
    }

    @GetMapping("/get-meals")
    public List<MealDto> getAllMeals(Principal principal) {
        return userService.getAllMeals(principal.getName());
    }


}
