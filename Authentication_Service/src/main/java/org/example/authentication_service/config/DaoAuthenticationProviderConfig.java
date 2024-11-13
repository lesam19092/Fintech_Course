package org.example.authentication_service.config;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.service.user_edadil.UserEdadilService;
import org.example.authentication_service.service.user_foodru.UserFoodRuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DaoAuthenticationProviderConfig {

    private final UserEdadilService userEdadilService;
    private final UserFoodRuService userFoodRuService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider edadilAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userEdadilService);
        return daoAuthenticationProvider;
    }

    @Bean
    public DaoAuthenticationProvider foodRuAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userFoodRuService);
        return daoAuthenticationProvider;
    }

}
