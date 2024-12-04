package org.example.foodru_microservice.handler.exception;

public class MealAlreadyExistsException extends RuntimeException {
    public MealAlreadyExistsException(String message) {
        super(message);
    }
}