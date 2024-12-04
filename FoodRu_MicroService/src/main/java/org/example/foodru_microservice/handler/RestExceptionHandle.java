package com.example.edadil_microservice.handler;

import com.example.edadil_microservice.handler.exception.EmptyResultException;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.handler.exception.NoCompaniesSellingFirmProductsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandle {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = String.format("Unsupported request method: %s. Supported methods: %s",
                ex.getMethod(), Arrays.toString(ex.getSupportedMethods()));
        log.error("HttpRequestMethodNotSupportedException: {}", message, ex);
        return new ApiError(HttpStatus.BAD_REQUEST, String.format("Сообщение: %s \n Ошибка: %s%n", message, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Expected argument of type %s, but received %s for parameter '%s'",
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName(), ex.getValue() == null ? "null" : ex.getValue().getClass().getSimpleName(), ex.getParameter());
        log.error("MethodArgumentTypeMismatchException: {}", message, ex);
        return new ApiError(HttpStatus.BAD_REQUEST, String.format("Сообщение: %s \n Ошибка: %s%n", message, ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Failed to read request body. Please ensure the request body is formatted correctly.";
        log.error("HttpMessageNotReadableException: {}", message, ex);
        return new ApiError(HttpStatus.BAD_REQUEST, String.format("Сообщение: %s \n Ошибка: %s%n", message, ex.getMessage()));
    }

    @ExceptionHandler(NoCompaniesSellingFirmProductsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoCompaniesSellingFirmProductsException(NoCompaniesSellingFirmProductsException ex) {
        log.error("NoCompaniesSellingFirmProductsException: {}", ex.getMessage(), ex);
        return new ApiError(HttpStatus.NOT_FOUND, String.format("Сообщение: Ошибка: %s%n", ex.getMessage()));
    }

    @ExceptionHandler(EmptyResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEmptyResultException(EmptyResultException ex) {
        log.error("EmptyResultException: {}", ex.getMessage(), ex);
        return new ApiError(HttpStatus.NOT_FOUND, String.format("Сообщение: Ошибка: %s%n", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage(), ex);
        return new ApiError(HttpStatus.NOT_FOUND, String.format("Сообщение: Ошибка: %s%n", ex.getMessage()));
    }

}
