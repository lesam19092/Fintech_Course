package org.example.authentication_service.service.logout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.model.entity.TokenEdadil;
import org.example.authentication_service.repository.TokenEdadilRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

    private final TokenEdadilRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        log.debug("Authorization header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        TokenEdadil storedToken = tokenRepository.findByAccessToken(token).orElse(null);

        if (storedToken != null) {
            log.debug("Token found: {}", storedToken);
            storedToken.setIsLoggedOut(true);
            tokenRepository.save(storedToken);
            log.info("Token marked as logged out and saved");
        } else {
            log.warn("Token not found for token: {}", token);
        }
    }
}