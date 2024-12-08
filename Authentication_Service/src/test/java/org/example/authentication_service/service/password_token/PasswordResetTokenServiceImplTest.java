package org.example.authentication_service.service.password_token;

import org.example.authentication_service.model.entity.PasswordResetToken;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetTokenServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private PasswordResetTokenServiceImpl passwordResetTokenService;

    @Test
    void save_whenCalled_savesToken() {
        User user = new User();
        user.setName("testUser");
        String token = "testToken";

        passwordResetTokenService.save(token, user);

        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    void delete_whenCalled_deletesToken() {
        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetTokenService.delete(passwordResetToken);

        verify(passwordResetTokenRepository, times(1)).delete(passwordResetToken);
    }

    @Test
    void generatePasswordResetToken_whenCalled_generatesAndSavesToken() {
        User user = new User();
        user.setName("testUser");

        String token = passwordResetTokenService.generatePasswordResetToken(user);

        assertNotNull(token);
        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

    @Test
    void generatePasswordResetToken_whenUserHasExistingToken_deletesOldToken() {
        User user = new User();
        user.setName("testUser");
        PasswordResetToken existingToken = new PasswordResetToken();
        user.setPasswordResetToken(existingToken);

        String token = passwordResetTokenService.generatePasswordResetToken(user);

        assertNotNull(token);
        verify(passwordResetTokenRepository, times(1)).delete(existingToken);
        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
    }

}