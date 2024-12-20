
package org.example.authentication_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.UpdatePasswordDto;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.service.password.ForgotPasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ForgotPasswordController.class)
@AutoConfigureMockMvc(addFilters = false)
class ForgotPasswordControllerTest {

    @MockBean
    private ForgotPasswordService passwordService;


    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    void updatePassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("newPassword");
        updatePasswordDto.setConfirmPassword("newPassword");
        String passwordToken = "validToken";
        String requestJson = mapper.writeValueAsString(updatePasswordDto);

        when(passwordService.updatePassword(updatePasswordDto, passwordToken))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.RESTORE_PASSWORD)
                        .param("passwordToken", passwordToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }


    @Test
    void resetPassword() throws Exception {
        PasswordResetRequest request = new PasswordResetRequest("user@example.com", "newPassword123");
        String expectedJson = mapper.writeValueAsString(request);

        when(passwordService.resetPassword(request))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.RESET_PASSWORD)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void resetPassword_NotFound() throws Exception {
        PasswordResetRequest request = new PasswordResetRequest("user@example.com", "newPassword123");
        String expectedJson = mapper.writeValueAsString(request);

        when(passwordService.resetPassword(request))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.RESET_PASSWORD)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePassword_UserNotFound() throws Exception {

        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setPassword("newPassword");
        updatePasswordDto.setConfirmPassword("newPassword");
        String passwordToken = "token";
        String requestJson = mapper.writeValueAsString(updatePasswordDto);

        when(passwordService.updatePassword(updatePasswordDto, passwordToken))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.RESTORE_PASSWORD)
                        .param("passwordToken", passwordToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}