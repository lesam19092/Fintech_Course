package org.example.foodru_microservice.service.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReceiptResponse {
    private String companyName;
    private String address;
    private List<IngredientResponse> ingredients = new ArrayList<>();
    private boolean hasMissingIngredients;
    private List<String> missingIngredients = new ArrayList<>();
    private double cost;
}