package org.example.authentication_service.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.exception.DuplicateUsernameException;
import org.example.authentication_service.exception.PasswordMismatchException;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.service.user_edadil.UserEdadilService;
import org.example.authentication_service.service.user_foodru.UserFoodRuService;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Checker {

    private final UserFoodRuService userFoodRuService;
    private final UserEdadilService userEdadilService;

    public void checkPassword(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            log.warn("Password mismatch for user: {}", registrationUserDto.getUsername());
            throw new PasswordMismatchException("Пароли не совпадают");
        }
    }

    public void checkUsername(RegistrationUserDto registrationUserDto, UserType userType) {
        if (userType == UserType.FoodRu) {
            if (userFoodRuService.existsByUsername(registrationUserDto.getUsername())) {
                log.warn("Duplicate username found: {}", registrationUserDto.getUsername());
                throw new DuplicateUsernameException("Пользователь с таким именем уже существует");
            }
        } else {
            if (userEdadilService.existsByUsername(registrationUserDto.getUsername())) {
                log.warn("Duplicate username found: {}", registrationUserDto.getUsername());
                throw new DuplicateUsernameException("Пользователь с таким именем уже существует");
            }
        }
    }
}
