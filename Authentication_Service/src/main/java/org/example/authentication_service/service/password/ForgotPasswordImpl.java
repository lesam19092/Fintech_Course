package org.example.authentication_service.service.password;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.UpdatePasswordDto;
import org.example.authentication_service.hadler.exception.PasswordMismatchException;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordImpl implements ForgotPasswordService {

    private final UserService userService;

    @Override

    public ResponseEntity<?> resetPassword(PasswordResetRequest request) throws MessagingException {
        log.info("Resetting password for mail: {}", request.getEmail());
        userService.resetPassword(request);
        return ResponseEntity.ok("Check email to reset password");
    }

    @Override
    public ResponseEntity<?> updatePassword(UpdatePasswordDto updatePasswordDto, String passwordToken) throws MessagingException {
        if (!updatePasswordDto.getPassword().equals(updatePasswordDto.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        User user = userService.findUserByPasswordToken(passwordToken);
        if (user.getPasswordResetToken().getUsed()) {
            return ResponseEntity.ok("Password already changed");
        }
        userService.updatePassword(user, updatePasswordDto.getPassword());
        return ResponseEntity.ok("Password updated successfully");
    }

}
