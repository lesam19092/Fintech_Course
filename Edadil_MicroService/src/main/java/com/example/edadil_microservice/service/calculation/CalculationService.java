package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.controller.dto.IngredientDto;
import com.example.edadil_microservice.controller.response.PaymentReceiptResponse;

import java.util.List;

public interface CalculationService {

    List<PaymentReceiptResponse> generatePaymentReceipt(List<IngredientDto> response);

    List<PaymentReceiptResponse> getPaymentsWithOutMissingIngredients(List<IngredientDto> response);

    PaymentReceiptResponse getTheCheapestPayments(List<IngredientDto> response);
}