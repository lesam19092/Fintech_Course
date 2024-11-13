package org.example.authentication_service.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


@Data
public class PasswordResetRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, message = "New password must be at least 6 characters long")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank")
    @Size(min = 6, message = "Confirm password must be at least 6 characters long")
    private String confirmPassword;

    @NotBlank(message = "Confirmation code cannot be blank")
    private String confirmationCode;
}