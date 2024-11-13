package org.example.authentication_service.exception;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiError {
    private int status;
    private String message;
    private LocalDate timestamp;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDate.now();
    }
}