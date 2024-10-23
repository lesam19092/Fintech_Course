package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.model.request.IngredientRequest;
import com.example.edadil_microservice.model.response.PaymentReceipt;

import java.io.IOException;
import java.util.List;

public interface CalculationService {

    List<PaymentReceipt> generatePaymentReceipt(List<IngredientRequest> response) throws IOException;

    List<PaymentReceipt> getPaymentsWithOutMissingIngredients(List<IngredientRequest> response);

    List<PaymentReceipt>  getTheCheapestPayments(List<IngredientRequest> response);
}