package org.example.authentication_service.controller;


import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.service.auth.AuthService;
import org.example.authentication_service.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class AuthUserController {


    private final AuthService authService;

    private final UserService userService;


    @PostMapping(EndPoints.LOGIN)
    public ResponseEntity<?> createAuthToken(@Valid
                                             @RequestBody
                                             LoginUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping(EndPoints.REGISTRATION)
    public ResponseEntity<?> createNewUser(@Valid
                                           @RequestBody
                                           RegistrationUserDto registrationUserDto) throws MessagingException {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping(EndPoints.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid
                                           @RequestBody
                                           PasswordResetRequest request) {
        return authService.resetPassword(request);
    }


    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam String confirmationToken) {
        return authService.confirmUserAccount(confirmationToken);
    }


}
