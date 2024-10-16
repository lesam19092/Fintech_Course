package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.model.request.IngredientRequest;
import com.example.edadil_microservice.model.response.IngredientResponse;

import java.util.List;

public interface CalculationService {

    List<IngredientResponse> generatePaymentReceipt(List<IngredientRequest> response);

    List<IngredientResponse> getPaymentsWithOutMissingIngredients(List<IngredientRequest> response);
}