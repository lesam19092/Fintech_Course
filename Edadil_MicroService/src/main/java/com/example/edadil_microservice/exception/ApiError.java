package com.example.edadil_microservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
class ApiError {

    //TODO придумать кастомные exception


    private HttpStatus status;
    private String message;
}