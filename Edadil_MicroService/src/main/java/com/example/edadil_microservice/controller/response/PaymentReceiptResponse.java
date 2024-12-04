package com.example.edadil_microservice.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentReceiptResponse {

    private String companyName;
    private String address;
    private List<IngredientResponse> ingredients;
    private boolean hasMissingIngredients;
    private List<String> missingIngredients;
    private double cost;


    public void addIngredient(IngredientResponse ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addMissingIngredient(String ingredientName) {
        if (ingredientName != null) {
            this.hasMissingIngredients = true;
            this.missingIngredients.add(ingredientName);
        }
    }

    public void setCost(double costs, double count) {
        this.cost += costs * count;
    }

}
