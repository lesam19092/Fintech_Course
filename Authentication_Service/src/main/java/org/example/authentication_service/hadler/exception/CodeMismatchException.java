package org.example.authentication_service.hadler.exception;

public class CodeMismatchException extends RuntimeException {
    public CodeMismatchException(String message) {
        super(message);
    }
}