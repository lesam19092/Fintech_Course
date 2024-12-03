package org.example.authentication_service.service.token;

import org.example.authentication_service.model.entity.Token;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    void saveToken_shouldSaveToken() {
        User user = new User();
        String token = "testToken";
        Token tokenEntity = new Token();
        tokenEntity.setAccessToken(token);
        tokenEntity.setIsLoggedOut(false);
        tokenEntity.setUser(user);

        when(tokenRepository.save(any(Token.class))).thenReturn(tokenEntity);

        Token result = tokenService.saveToken(user, token);

        assertNotNull(result);
        assertEquals(token, result.getAccessToken());
        assertEquals(user, result.getUser());
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void revokeAllTokenByUser_shouldRevokeAllTokens() {
        User user = new User();
        user.setId(1L);
        Token token1 = new Token();
        token1.setIsLoggedOut(false);
        Token token2 = new Token();
        token2.setIsLoggedOut(false);
        List<Token> tokens = List.of(token1, token2);

        when(tokenRepository.findAllAccessTokensByUser(user.getId())).thenReturn(tokens);

        tokenService.revokeAllTokenByUser(user);

        assertTrue(tokens.stream().allMatch(Token::getIsLoggedOut));
        verify(tokenRepository, times(1)).saveAll(tokens);
    }
}