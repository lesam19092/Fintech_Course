package org.example.authentication_service.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
public class PasswordResetRequest {

    @NotEmpty(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "User type cannot be blank")
    private String userType;
}