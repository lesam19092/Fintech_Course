package main.java.org.example.authentication_service.exception;

import main.java.org.example.authentication_service.exception.ApiError;
import main.java.org.example.authentication_service.exception.CodeMismatchException;
import main.java.org.example.authentication_service.exception.DuplicateUsernameException;
import main.java.org.example.authentication_service.exception.PasswordMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandle {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(BadCredentialsException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlePasswordMismatchException(PasswordMismatchException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handelDuplicateUsernameException(DuplicateUsernameException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handelUsernameNotFoundException(DuplicateUsernameException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Пользователь c указанным именем не найдем "), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CodeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlePasswordMismatchException(CodeMismatchException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), "Invalid confirmation code"), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}