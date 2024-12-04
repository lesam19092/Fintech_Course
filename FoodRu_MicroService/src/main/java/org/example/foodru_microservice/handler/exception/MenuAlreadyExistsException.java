package org.example.foodru_microservice.handler.exception;

public class MenuAlreadyExistsException extends RuntimeException {
    public MenuAlreadyExistsException(String message) {
        super(message);
    }
}
