package org.example.authentication_service.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.authentication_service.model.consts.UserType;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginUserDto {

    /**
     * {
     * "username": "даня",
     * "password": "123456",
     * "rememberMe": false
     * }
     */

    @NotEmpty(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotEmpty(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotEmpty
    private UserType userType;

    private Boolean rememberMe;
}