package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.model.dto.IngredientDto;
import com.example.edadil_microservice.model.response.PaymentReceiptResponse;
import com.example.edadil_microservice.service.calculation.CalculationServiceImpl;
import com.example.edadil_microservice.service.kafka.KafkaProducer;
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

    private final KafkaProducer kafkaProducer;


    //TODO потом сделать

    @GetMapping("/products")
    public List<PaymentReceiptResponse> getAllProducts() throws IOException {

    //    kafkaProducer.sendMessage("привет от Edadil");


        IngredientDto ingridientItem1 = new IngredientDto();

        ingridientItem1.setMeasure(2.0);
        ingridientItem1.setName("Чипсы");

        IngredientDto ingridientItem2 = new IngredientDto();

        ingridientItem2.setMeasure(1.0);
        ingridientItem2.setName("Чай");





        List<IngredientDto> list = new ArrayList<>();
        list.add(ingridientItem1);
        list.add(ingridientItem2);

        return calculationService.getTheCheapestPayments(list);
    }


}
