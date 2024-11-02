package com.example.edadil_microservice.model.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentReceiptResponse {


    private String companyName;
    private String address;
    private List<IngredientResponse> ingredients = new ArrayList<>();
    private boolean hasMissingIngredients;
    private List<String> missingIngredients = new ArrayList<>();
    private double cost;


    public void addIngredient(IngredientResponse ingredient) {

        this.ingredients.add(ingredient);
    }

    public void addMissingIngredient(String ingredientName) {
        this.hasMissingIngredients = true;
        this.missingIngredients.add(ingredientName);
    }

    public void setCost(double costs, double count) {
        this.cost += costs * count;
    }

    @Override
    public String toString() {
        return "PaymentReceipt{" +
                "companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", ingredients=" + ingredients +
                ", hasMissingIngredients=" + hasMissingIngredients +
                ", missingIngredients=" + missingIngredients +
                ", cost=" + cost +
                '}';
    }
}