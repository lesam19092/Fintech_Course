package org.example.authentication_service.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


@Data
public class RegistrationUserDto {


    /**
     * {
     * "username": "даня",
     * "password": "123456",
     * "confirmPassword" : "123456",
     * "email": "danigpro1337@gmail.com"
     * }
     */


    @NotBlank
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
}