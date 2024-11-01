package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.model.dto.IngredientDto;
import com.example.edadil_microservice.model.response.PaymentReceiptResponse;

import java.io.IOException;
import java.util.List;

public interface CalculationService {

    List<PaymentReceiptResponse> generatePaymentReceipt(List<IngredientDto> response) throws IOException;

    List<PaymentReceiptResponse> getPaymentsWithOutMissingIngredients(List<IngredientDto> response);

    List<PaymentReceiptResponse>  getTheCheapestPayments(List<IngredientDto> response);
}