package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.IntegrationTestBase;
import com.example.edadil_microservice.controller.dto.IngredientDto;
import com.example.edadil_microservice.handler.exception.EmptyResultException;
import com.example.edadil_microservice.service.kafka.response.PaymentReceiptResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CalculationServiceImplTest extends IntegrationTestBase {

    @Autowired
    private CalculationService calculationService;

    @Test
    void generatePaymentReceipt_whenValidIngredientsProvided_generatesReceipts() {
        List<IngredientDto> ingredients = List.of(
                new IngredientDto("Молоко", 2.0d),
                new IngredientDto("Кефир", 1.0d)
        );

        List<PaymentReceiptResponse> receipts = calculationService.generatePaymentReceipt(ingredients);

        PaymentReceiptResponse receipt1 = receipts.get(0);
        PaymentReceiptResponse receipt2 = receipts.get(1);


        assertAll(
                "Should generate receipts for all shops with matching products",
                () -> assertEquals(11, receipts.size()),
                () -> assertEquals(135.0d, receipt1.getCost()),
                () -> assertEquals(150.0d, receipt2.getCost())
        );
    }

    @Test
    void getTheCheapestPayments_whenValidIngredientsProvided_returnsCheapestReceipt() {
        List<IngredientDto> ingredients = List.of(
                new IngredientDto("Молоко", 2.d),
                new IngredientDto("Кефир", 1.d)
        );

        PaymentReceiptResponse cheapestReceipt = calculationService.getTheCheapestPayments(ingredients);


        assertEquals(135.0d, cheapestReceipt.getCost(), "Cost of the cheapest receipt should be 135.0");
    }

    @Test
    void getTheCheapestPayments_whenNoValidPayments_throwsException() {
        List<IngredientDto> ingredients = List.of(
                new IngredientDto("Несуществующий продукт", 1.d)
        );

        assertThrows(EmptyResultException.class,
                () -> calculationService.getTheCheapestPayments(ingredients),
                "Expected EmptyResultException for ingredients without valid payments"
        );
    }
}
