package org.example.authentication_service.service.token;



import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.entity.Token;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;


    @Override
    public Token saveToken(User user, String token) {
        Token tokenEntity = new Token();
        tokenEntity.setAccessToken(token);
        tokenEntity.setIsLoggedOut(false);
        tokenEntity.setUser(user);
        tokenRepository.save(tokenEntity);
        return tokenEntity;
    }

    @Override
    public void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> t.setIsLoggedOut(true));
            tokenRepository.saveAll(validTokens);
        }
    }

    @Override
    public boolean isValid(String token) {
        return tokenRepository
                .findByAccessToken(token)
                .map(t -> !t.getIsLoggedOut())
                .orElse(false);
    }
}