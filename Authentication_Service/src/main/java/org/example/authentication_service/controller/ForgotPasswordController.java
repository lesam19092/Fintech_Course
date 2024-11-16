package org.example.authentication_service.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.UpdatePasswordDto;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.service.password.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class ForgotPasswordController {


    private final ForgotPasswordService passwordService;


    @PostMapping(EndPoints.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid
                                           @RequestBody
                                           PasswordResetRequest request) throws MessagingException {
        return passwordService.resetPassword(request);
    }

    @PostMapping(EndPoints.RESTORE_PASSWORD)
    public ResponseEntity<?> updatePassword(
                                    @RequestParam String passwordToken,
                                    @RequestBody UpdatePasswordDto updatePasswordDto) throws MessagingException {

        return passwordService.updatePassword(updatePasswordDto, passwordToken);

    }
}
