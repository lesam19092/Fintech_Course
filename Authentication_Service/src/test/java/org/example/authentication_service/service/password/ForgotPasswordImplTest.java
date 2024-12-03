package org.example.authentication_service.service.password;

import jakarta.mail.MessagingException;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.UpdatePasswordDto;
import org.example.authentication_service.handler.exception.PasswordMismatchException;
import org.example.authentication_service.model.entity.PasswordResetToken;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordImplTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private ForgotPasswordImpl forgotPasswordService;


    @Test
    void resetPasswordTest() throws MessagingException {
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail("test@example.com");

        doNothing().when(userServiceMock).resetPassword(request);

        ResponseEntity<?> response = forgotPasswordService.resetPassword(request);

        assertEquals("Check email to reset password", response.getBody());
        verify(userServiceMock, times(1)).resetPassword(request);
    }


    @Test
    void updatePasswordTest() throws MessagingException {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("newPassword");
        updatePasswordDto.setConfirmPassword("newPassword");

        User user = new User();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUsed(false);
        user.setPasswordResetToken(passwordResetToken);

        when(userServiceMock.findUserByPasswordToken("token")).thenReturn(user);
        doNothing().when(userServiceMock).updatePassword(user, "newPassword");

        ResponseEntity<?> response = forgotPasswordService.updatePassword(updatePasswordDto, "token");

        assertEquals("Password updated successfully", response.getBody());
        verify(userServiceMock, times(1)).findUserByPasswordToken("token");
        verify(userServiceMock, times(1)).updatePassword(user, "newPassword");
    }

    @Test
    void updatePasswordMismatchTest() {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("newPassword");
        updatePasswordDto.setConfirmPassword("differentPassword");

        Exception exception = assertThrows(PasswordMismatchException.class, () -> {
            forgotPasswordService.updatePassword(updatePasswordDto, "token");
        });

        assertEquals("Passwords do not match", exception.getMessage());
    }

    @Test
    void updatePasswordAlreadyChangedTest() throws MessagingException {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("newPassword");
        updatePasswordDto.setConfirmPassword("newPassword");

        User user = new User();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUsed(true);
        user.setPasswordResetToken(passwordResetToken);

        when(userServiceMock.findUserByPasswordToken("token")).thenReturn(user);

        ResponseEntity<?> response = forgotPasswordService.updatePassword(updatePasswordDto, "token");

        assertEquals("Password already changed", response.getBody());
        verify(userServiceMock, times(1)).findUserByPasswordToken("token");
    }


}