package org.example.authentication_service.service.check;

import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.handler.exception.DuplicateEmailException;
import org.example.authentication_service.handler.exception.DuplicateUsernameException;
import org.example.authentication_service.handler.exception.PasswordMismatchException;
import org.example.authentication_service.handler.exception.UserNotVerifiedException;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;


@SpringBootTest
class CheckServiceImplTest {

    @MockBean
    private UserService userService;

    @Autowired
    private CheckServiceImpl checkService;





    @Test
    void checkUser_validRegistration() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("validUsername");
        registrationUserDto.setEmail("validEmail@example.com");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("password");
        registrationUserDto.setUserType(UserType.Edadil);

        when(userService.existsByUsernameAndInstance(registrationUserDto.getUsername(), registrationUserDto.getUserType().toString())).thenReturn(false);

        assertDoesNotThrow(() -> checkService.checkUser(registrationUserDto));
    }

    @Test
    void checkVerification_userVerified() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("verifiedUser");
        loginUserDto.setUserType(UserType.Edadil);

        when(userService.isVerified(loginUserDto.getUsername(), loginUserDto.getUserType().toString())).thenReturn(true);

        assertDoesNotThrow(() -> checkService.checkVerification(loginUserDto));
    }


    @Test
    void checkUser_passwordMismatch_throwsPasswordMismatchException() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("differentPassword");
        registrationUserDto.setUserType(UserType.Edadil);

        assertThrows(PasswordMismatchException.class, () -> checkService.checkUser(registrationUserDto));
    }

    @Test
    void checkUser_duplicateUsername_throwsDuplicateUsernameException() {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setUsername("username");
        userDto.setUserType(UserType.Edadil);

        when(userService.existsByUsernameAndInstance(userDto.getUsername(), userDto.getUserType().toString())).thenReturn(true);

        assertThrows(DuplicateUsernameException.class, () -> checkService.checkUser(userDto));
    }

    @Test
    void checkUser_duplicateEmail_throwsDuplicateEmailException() {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setEmail("email@example.com");
        userDto.setUserType(UserType.FoodRu);

        when(userService.existsByEmailAndInstance(userDto.getEmail(), userDto.getUserType().toString())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> checkService.checkUser(userDto));
    }

    @Test
    void checkVerification_userNotVerified_throwsUserNotVerifiedException() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("username");
        loginUserDto.setUserType(UserType.Edadil);

        when(userService.isVerified(loginUserDto.getUsername(), loginUserDto.getUserType().toString())).thenReturn(false);

        assertThrows(UserNotVerifiedException.class, () -> checkService.checkVerification(loginUserDto));
    }
}