package com.example.edadil_microservice.service.kafka;

import com.example.edadil_microservice.controller.dto.ListIngredientDto;
import com.example.edadil_microservice.service.calculation.CalculationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class KafkaConsumer {


    private final CalculationService calculationService;

    @KafkaListener(topics = "${spring.properties.kafka.topicFoodRuToEdadil}", groupId = "${spring.properties.kafka.groupId}")
    public void listenGroupFoo(ListIngredientDto listIngredientDto) {
        System.out.println("Received Message in group foo: " + listIngredientDto.toString());
        calculationService.getTheCheapestPayments(listIngredientDto.getIngredientDtoList());
    }


}
