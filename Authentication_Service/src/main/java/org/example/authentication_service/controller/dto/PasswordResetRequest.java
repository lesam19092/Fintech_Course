package org.example.authentication_service.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class PasswordResetRequest {

    @NotEmpty(message = "Name cannot be blank")
    private String name;

    @NotEmpty(message = "New password cannot be blank")
    @Size(min = 6, message = "New password must be at least 6 characters long")
    private String newPassword;

    @NotEmpty(message = "Confirm password cannot be blank")
    @Size(min = 6, message = "Confirm password must be at least 6 characters long")
    private String confirmPassword;

    @NotEmpty(message = "Confirmation code cannot be blank")
    private String confirmationCode;
}