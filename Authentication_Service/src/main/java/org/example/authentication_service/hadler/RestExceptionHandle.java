package org.example.authentication_service.hadler;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.hadler.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.CompletionException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandle {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(BadCredentialsException ex) {
        log.error("BadCredentialsException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException ex) {
        log.error("PasswordMismatchException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        log.error("DuplicateUsernameException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь c указанным именем не найдем "), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CodeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleCodeMismatchException(CodeMismatchException ex) {
        log.error("CodeMismatchException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid confirmation code"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGenericException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.format("Сообщение: Внутренняя ошибка сервера\nОшибка: %s%n", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmailException(DuplicateEmailException ex) {
        log.error("DuplicateEmailException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным email уже существует"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<?> handleUserNotVerifiedException(UserNotVerifiedException ex) {
        log.error("UserNotVerifiedException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь не подтвержден"), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("EntityNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Entity not found"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompletionException.class)
    public ResponseEntity<?> handleCompletionException(CompletionException ex) {
        log.error("CompletionException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Completion exception"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException ex) {
        log.error("MessagingException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "к сожалению, не удалось отправить сообщение на почту"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(EmailNotFoundException ex) {
        log.error("EmailNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным email не найден"), HttpStatus.BAD_REQUEST);
    }


}