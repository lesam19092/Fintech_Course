package org.example.foodru_microservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.model.consts.endpoints.UserEndPoints;
import org.example.foodru_microservice.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(UserEndPoints.ADD_MEAL)
    public ResponseEntity<String> addMeal(@PathVariable Long id, Principal principal) {
        boolean added = userService.addMeal(id, principal.getName());
        return ResponseEntity.ok(added ? "Meal added" : "Meal was already added");
    }

    @PostMapping(UserEndPoints.CREATE_MENU)
    public ResponseEntity<String> createMenu(Principal principal, @PathVariable String menuName) {
        boolean created = userService.createMenu(principal.getName(), menuName);
        return ResponseEntity.ok(created ? "Menu created" : "Menu was already created");
    }


}
