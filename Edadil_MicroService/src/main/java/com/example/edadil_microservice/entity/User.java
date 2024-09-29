package com.example.edadil_microservice.entity;

import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class User {

    private Long id;
    @Size(min = 2, message = "Не меньше 2 знаков")
    private String username;
    @Size(min = 2, message = "Не меньше 2 знаков")
    private String password;
    private String passwordConfirm;
    private Role role;
}
