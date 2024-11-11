package org.example.authentication_service.exception;

public class CodeMismatchException extends RuntimeException {
    public CodeMismatchException(String message) {
        super(message);
    }
}