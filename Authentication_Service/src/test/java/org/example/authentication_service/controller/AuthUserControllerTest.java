package org.example.authentication_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createAuthToken() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto("username", "password", UserType.Edadil, false);
        String expectedJson = mapper.writeValueAsString(loginUserDto);

        when(authService.createAuthToken(Mockito.any(LoginUserDto.class)))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.LOGIN)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createNewUser() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto("username", "password", "password", "email@example.com", UserType.Edadil);
        String expectedJson = mapper.writeValueAsString(registrationUserDto);

        when(authService.createNewUser(Mockito.any(RegistrationUserDto.class)))
                .thenReturn(ResponseEntity.status(201).build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.REGISTRATION)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void confirmUserAccount() throws Exception {
        String confirmationToken = "token";

        when(authService.confirmUserAccount(confirmationToken))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get(EndPoints.AUTH + EndPoints.CONFIRM_ACCOUNT)
                        .param("confirmationToken", confirmationToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createAuthToken_withInvalidCredentials_shouldReturnUnauthorized() throws Exception {
        LoginUserDto loginUserDto = new LoginUserDto("invalidUsername", "invalidPassword", UserType.Edadil, false);
        String expectedJson = mapper.writeValueAsString(loginUserDto);

        when(authService.createAuthToken(Mockito.any(LoginUserDto.class)))
                .thenReturn(ResponseEntity.status(401).build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.LOGIN)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
    @Test
    void createNewUser_withExistingUsername_shouldReturnConflict() throws Exception {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto("existingUsername", "password", "password", "email@example.com", UserType.Edadil);
        String expectedJson = mapper.writeValueAsString(registrationUserDto);

        when(authService.createNewUser(Mockito.any(RegistrationUserDto.class)))
                .thenReturn(ResponseEntity.status(409).build());

        mockMvc.perform(post(EndPoints.AUTH + EndPoints.REGISTRATION)
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
    @Test
    void confirmUserAccount_withInvalidToken_shouldReturnBadRequest() throws Exception {
        String invalidToken = "invalidToken";

        when(authService.confirmUserAccount(invalidToken))
                .thenReturn(ResponseEntity.status(400).build());

        mockMvc.perform(get(EndPoints.AUTH + EndPoints.CONFIRM_ACCOUNT)
                        .param("confirmationToken", invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}