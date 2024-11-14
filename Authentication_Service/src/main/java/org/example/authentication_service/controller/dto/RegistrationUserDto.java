package org.example.authentication_service.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.authentication_service.model.consts.UserType;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotEmpty;


@Data
public class RegistrationUserDto {


    /**
     * {
     * "username": "даня",
     * "password": "123456",
     * "confirmPassword" : "123456",
     * "email": "danigpro1337@gmail.com",
     * "userType": "FoodRu"
     * }
     */


    @NotEmpty(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotEmpty(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty
    private UserType userType;
}
//todo написать отрпавление аккаунта на почту
//todo сделать сброс через почту
