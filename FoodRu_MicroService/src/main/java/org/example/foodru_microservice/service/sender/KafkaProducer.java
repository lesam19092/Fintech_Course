package org.example.foodru_microservice.service.sender;

public interface KafkaProducer {

    void sendMessage(String msg);
}
