package org.example.authentication_service.controller.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdatePasswordDto {

    @NotEmpty(message = "Email cannot be blank")
    @NotNull
    private String password;

    @NotEmpty(message = "Email cannot be blank")
    @NotNull
    private String confirmPassword;
}
