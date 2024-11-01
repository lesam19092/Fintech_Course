package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.model.response.PaymentReceiptResponse;
import com.example.edadil_microservice.service.calculation.CalculationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {


    private final CalculationServiceImpl calculationService;


    //TODO потом сделать

    @GetMapping("/products")
    public List<PaymentReceiptResponse> getAllProducts() throws IOException {

/*
        IngredientDto ingridientItem1 = new IngredientDto();

        ingridientItem1.setMeasure(2);
        ingridientItem1.setName("Чипсы");

        IngredientDto ingridientItem2 = new IngredientDto();

        ingridientItem2.setMeasure(1);
        ingridientItem2.setName("Чай");

        IngredientDto ingridientItem3 = new IngredientDto();

        ingridientItem3.setMeasure(1);
        ingridientItem3.setName("Мясо");


        List<IngredientDto> list = new ArrayList<>();
        list.add(ingridientItem1);
        list.add(ingridientItem2);
        list.add(ingridientItem3);

        return calculationService.generatePaymentReceipt(list);*/
        return null;
    }


}
