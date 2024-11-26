package org.example.authentication_service.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.UpdatePasswordDto;
import org.example.authentication_service.handler.ApiError;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.service.password.ForgotPasswordService;
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
public class ForgotPasswordController {

    private final ForgotPasswordService passwordService;

    @Operation(summary = "Reset Password", description = "Send a password reset link to the user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset link sent",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(EndPoints.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetRequest request) throws MessagingException {
        return passwordService.resetPassword(request);
    }

    @Operation(summary = "Update Password", description = "Update the user's password using a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid token or password details",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping(EndPoints.RESTORE_PASSWORD)
    public ResponseEntity<?> updatePassword(@RequestParam String passwordToken, @RequestBody UpdatePasswordDto updatePasswordDto) throws MessagingException {
        return passwordService.updatePassword(updatePasswordDto, passwordToken);
    }
}