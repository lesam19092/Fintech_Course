package com.example.edadil_microservice.service.kafka;

import com.example.edadil_microservice.model.dto.ListIngredientDto;
import com.example.edadil_microservice.model.response.PaymentReceiptResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Data
public class KafkaConsumer {


    //todo сделать так , чтобы те сообщения , которые не удалось обработать сразу - отправлялись позже
    @KafkaListener(topics = "${spring.properties.kafka.topicFoodRuToEdadil}", groupId = "${spring.properties.kafka.groupId}")
    public void listenGroupFoo(ListIngredientDto listIngredientDto) {
        System.out.println("Received Message in group foo: " + listIngredientDto.toString());
        // Additional processing logic can be added here
    }




    /*
    @KafkaListener(topics = {"${kafka.consumer.consumer1.topic}"}, groupId = "${kafka.consumer.consumer1.group-id}",
      containerFactory = "consumer1ContainerFactory")
    public void receive(@Payload String message) {
        log.info("message received in consumer1: {}", message);
    }
     */
}
