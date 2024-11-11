package main.java.org.example.authentication_service.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {
    private int status;
    private String message;
    private Date timestamp;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}