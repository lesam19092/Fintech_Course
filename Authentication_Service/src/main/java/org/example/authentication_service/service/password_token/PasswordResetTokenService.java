package org.example.authentication_service.service.password_token;

import org.example.authentication_service.model.entity.PasswordResetToken;
import org.example.authentication_service.model.entity.User;

public interface PasswordResetTokenService {


    void save(String token, User user);

    void delete(PasswordResetToken passwordResetToken);

    String generatePasswordResetToken(User user);

}
