package com.example.edadil_microservice.service.kafka;

import com.example.edadil_microservice.model.request.IngredientRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {


    //todo сделать так , чтобы те сообщения , которые не удалось обработать сразу - отправлялись позже
    @KafkaListener(topics = "topicFoodRuToEdadil", groupId = "myGroup")
    public void listenGroupFoo(IngredientRequest message) {
        System.out.println(message.getName());
        System.out.println(message.getCount());
        System.out.println("Received Message in group foo: " + message.toString());
    }
    //todo вынести топики в проперти
}
