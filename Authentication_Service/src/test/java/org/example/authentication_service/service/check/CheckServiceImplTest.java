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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class CheckServiceImplTest {


    @InjectMocks
    private CheckServiceImpl checkService;
    @Mock
    private UserService userService;


    @Test
    void testCheckVerification_UserNotVerified() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("testUser");
        loginUserDto.setUserType(UserType.Edadil);

        Mockito.when(userService.isVerified("testUser", UserType.Edadil.name())).thenReturn(false);

        assertThrows(UserNotVerifiedException.class, () -> checkService.checkVerification(loginUserDto));
    }

    @Test
    void testCheckPassword_PasswordMismatch() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("differentPassword");

        assertThrows(PasswordMismatchException.class, () -> checkService.checkPassword(registrationUserDto));
    }

    @Test
    void testCheckUsername_DuplicateUsername() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("testUser");
        registrationUserDto.setUserType(UserType.Edadil);

        Mockito.when(userService.existsByUsernameAndInstance("testUser", UserType.Edadil.name())).thenReturn(true);

        assertThrows(DuplicateUsernameException.class, () -> checkService.checkUsername(registrationUserDto));
    }

    @Test
    void testCheckEmail_DuplicateEmail() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setEmail("test@example.com");
        registrationUserDto.setUserType(UserType.Edadil);

        Mockito.when(userService.existsByEmailAndInstance("test@example.com", UserType.Edadil.name())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> checkService.checkEmail(registrationUserDto));
    }

}




