package org.example.foodru_microservice.service.kafka;

import lombok.Data;
import org.example.foodru_microservice.model.dto.kafka.PaymentReceiptResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Data
public class KafkaConsumer {


    //todo сделать так , чтобы те сообщения , которые не удалось обработать сразу - отправлялись позже
    @KafkaListener(topics = "${spring.properties.kafka.topicEdadilToFoodRu}", groupId = "${spring.properties.kafka.groupId}")
    public void listenGroupFoo(PaymentReceiptResponse paymentReceiptResponse) {
        System.out.println("Received Message in group foo: " + paymentReceiptResponse.toString());
    }
}