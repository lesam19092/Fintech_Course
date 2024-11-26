package org.example.authentication_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.handler.ApiError;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthService authService;

    @Operation(summary = "Create Authentication Token", description = "Generate a new authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication token created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid login details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(EndPoints.LOGIN)
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody LoginUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @Operation(summary = "Register New User", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(EndPoints.REGISTRATION)
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @Operation(summary = "Confirm User Account", description = "Confirm a user account using a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account confirmed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid confirmation token",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(EndPoints.CONFIRM_ACCOUNT)
    public ResponseEntity<?> confirmUserAccount(@RequestParam String confirmationToken) {
        return authService.confirmUserAccount(confirmationToken);
    }
}