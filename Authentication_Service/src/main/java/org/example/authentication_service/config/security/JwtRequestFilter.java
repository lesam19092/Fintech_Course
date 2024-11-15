package org.example.authentication_service.config.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.service.jwt.JwtTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final int BEARER_PREFIX_LENGTH = 7;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";


    private final JwtTokenService jwtTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            jwt = authHeader.substring(BEARER_PREFIX_LENGTH);
            try {
                username = jwtTokenService.getUsername(jwt);
            } catch (ExpiredJwtException e) {
                log.error("Время жизни токена вышло");
            } catch (SignatureException e) {
                log.error("Подпись неправильная");
            }

        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenService.isValid(jwt)) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokenService.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }

}