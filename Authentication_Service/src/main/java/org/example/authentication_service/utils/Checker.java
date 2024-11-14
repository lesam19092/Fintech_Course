package org.example.authentication_service.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.exception.DuplicateUsernameException;
import org.example.authentication_service.exception.PasswordMismatchException;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.service.user.UserService;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Checker {

    private final UserService userService;


    public void checkPassword(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            log.warn("Password mismatch for user: {}", registrationUserDto.getUsername());
            throw new PasswordMismatchException("Пароли не совпадают");
        }
    }

    public void checkUsername(RegistrationUserDto registrationUserDto) {
        if (userService.existsByUsernameAndInstance(registrationUserDto.getUsername(), registrationUserDto.getUserType().name())) {
            log.warn("Duplicate username found: {}", registrationUserDto.getUsername());
            throw new DuplicateUsernameException("Пользователь с таким именем уже существует");
        }
    }
}