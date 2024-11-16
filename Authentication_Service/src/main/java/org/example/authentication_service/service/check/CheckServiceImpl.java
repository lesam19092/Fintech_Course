package org.example.authentication_service.service.check;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.hadler.exception.DuplicateEmailException;
import org.example.authentication_service.hadler.exception.DuplicateUsernameException;
import org.example.authentication_service.hadler.exception.PasswordMismatchException;
import org.example.authentication_service.hadler.exception.UserNotVerifiedException;
import org.example.authentication_service.service.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.CompletableFuture.runAsync;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckServiceImpl implements CheckService {

    private final UserService userService;
    @Qualifier("userDataProcessingService")
    private final ExecutorService userDataProcessingService;


    @Override
    public void checkUser(RegistrationUserDto registrationUserDto) {

        CompletableFuture<Void> passwordFuture =
               runAsync(() -> checkPassword(registrationUserDto), userDataProcessingService);

        CompletableFuture<Void> nameFuture =
                runAsync(() -> checkUsername(registrationUserDto), userDataProcessingService);

        CompletableFuture<Void> emailFuture =
                runAsync(() -> checkEmail(registrationUserDto), userDataProcessingService);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(nameFuture, emailFuture, passwordFuture);

        try {
            allOf.get();
        } catch (ExecutionException e) {
            throw (RuntimeException) e.getCause();
        } catch (InterruptedException e) {
            log.error("Error during data initialization", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void checkVerification(LoginUserDto loginUserDto) {
        if (!userService.isVerified(loginUserDto.getUsername(), loginUserDto.getUserType().name())) {
            log.warn("User not verified: {}", loginUserDto.getUsername());
            throw new UserNotVerifiedException("User is not verified");
        }
    }


    private void checkPassword(RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
    }

    private void checkUsername(RegistrationUserDto registrationUserDto) {
        if (userService.existsByUsernameAndInstance(registrationUserDto.getUsername(), registrationUserDto.getUserType().name())) {
            throw new DuplicateUsernameException("Пользователь с таким именем уже существует");
        }
    }

    private void checkEmail(RegistrationUserDto registrationUserDto) {
        if (userService.existsByEmailAndInstance(registrationUserDto.getEmail(), registrationUserDto.getUserType().name())) {
            throw new DuplicateEmailException("Пользователь с таким email уже существует");
        }
    }

}