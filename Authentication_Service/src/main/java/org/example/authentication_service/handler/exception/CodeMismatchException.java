package org.example.authentication_service.handler.exception;

public class CodeMismatchException extends RuntimeException {
    public CodeMismatchException(String message) {
        super(message);
    }
}