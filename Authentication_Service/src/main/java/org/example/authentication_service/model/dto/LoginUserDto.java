package org.example.authentication_service.model.dto;

import lombok.Data;

@Data
public class LoginUserDto {

    /**
     * {
     *     "username": "даня",
     *     "password": "123456",
     *     "rememberMe": false
     * }
     */

    private String username;
    private String password;
    private Boolean rememberMe;
}