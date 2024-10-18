package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.request.IngredientRequest;
import com.example.edadil_microservice.model.response.PaymentReceipt;
import com.example.edadil_microservice.service.calculation.CalculationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {


    private final CalculationServiceImpl calculationService;


    //TODO потом сделать

    @GetMapping("/products")
    public List<PaymentReceipt> getAllProducts() throws IOException {


        IngredientRequest ingridientItem1 = new IngredientRequest();

        ingridientItem1.setCount(2);
        ingridientItem1.setName("Чипсы");

        IngredientRequest ingridientItem2 = new IngredientRequest();

        ingridientItem2.setCount(1);
        ingridientItem2.setName("Чай");

        IngredientRequest ingridientItem3 = new IngredientRequest();

        ingridientItem3.setCount(1);
        ingridientItem3.setName("Мясо");


        List<IngredientRequest> list = new ArrayList<>();
        list.add(ingridientItem1);
        list.add(ingridientItem2);
        list.add(ingridientItem3);

        return calculationService.generatePaymentReceipt(list);
    }


}
