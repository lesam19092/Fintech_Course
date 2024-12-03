package org.example.foodru_microservice.handler.exception;

public class InvalidInstanceException extends RuntimeException {
    public InvalidInstanceException(String message) {
        super(message);
    }
}