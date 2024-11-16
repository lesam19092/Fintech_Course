package org.example.authentication_service.service.password_token;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.model.entity.PasswordResetToken;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void save(String token, User user) {
        log.info("Saving password reset token for user: {}", user.getName());
        PasswordResetToken p = PasswordResetToken.builder().token(token).used(false).user(user).build();
        user.setPasswordResetToken(p);
        passwordResetTokenRepository.save(p);
    }

    @Override
    public void delete(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
    }
}
