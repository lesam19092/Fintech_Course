package org.example.authentication_service.service.confirm_token;

import org.example.authentication_service.model.entity.ConfirmationToken;

public interface ConfirmTokenService {


    void saveConfirmToken(ConfirmationToken confirmToken);

    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
