package org.example.foodru_microservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.api.PurchaseApi;
import org.example.foodru_microservice.model.consts.endpoints.PurchaseEndPoints;
import org.example.foodru_microservice.service.purchase.PurchaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PurchaseController implements PurchaseApi {

    private final PurchaseService purchaseService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(PurchaseEndPoints.BUY_MEAL)
    public String buyCheapestMealIngredients(@PathVariable Long id, Principal principal) throws IOException {
        purchaseService.buyCheapestMealsIngredients(id, principal.getName());
        return "Payment was successful";
    }
}
