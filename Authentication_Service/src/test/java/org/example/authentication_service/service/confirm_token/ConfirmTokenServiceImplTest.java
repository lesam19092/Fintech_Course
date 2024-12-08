package org.example.authentication_service.service.confirm_token;

import jakarta.persistence.EntityNotFoundException;
import org.example.authentication_service.model.entity.ConfirmationToken;
import org.example.authentication_service.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ConfirmTokenServiceImplTest {


    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmTokenServiceImpl confirmTokenServiceImpl;

    private ConfirmationToken confirmationToken;

    @BeforeEach
    void setUp() {
        confirmationToken = new ConfirmationToken();
        confirmationToken.setToken("testToken");
    }

    @Test
    void saveConfirmToken() {
        when(confirmationTokenRepository.save(confirmationToken)).thenReturn(confirmationToken);
        confirmTokenServiceImpl.saveConfirmToken(confirmationToken);
        verify(confirmationTokenRepository).save(confirmationToken);
    }

    @Test
    void findByConfirmationToken() {
        when(confirmationTokenRepository.findByToken("testToken")).thenReturn(Optional.of(confirmationToken));
        ConfirmationToken foundToken = confirmTokenServiceImpl.findByConfirmationToken("testToken");
        assertEquals(confirmationToken, foundToken);
    }

    @Test
    void findByConfirmationToken_NotFound() {
        when(confirmationTokenRepository.findByToken("invalidToken")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            confirmTokenServiceImpl.findByConfirmationToken("invalidToken");
        });
    }
}