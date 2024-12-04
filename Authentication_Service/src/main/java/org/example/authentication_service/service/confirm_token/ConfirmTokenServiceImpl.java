package org.example.authentication_service.service.confirm_token;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.entity.ConfirmationToken;
import org.example.authentication_service.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmTokenServiceImpl implements ConfirmTokenService {


    private final ConfirmationTokenRepository confirmationTokenRepository;


    @Override
    public void saveConfirmToken(ConfirmationToken confirmToken) {
        confirmationTokenRepository.save(confirmToken);
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return confirmationTokenRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new EntityNotFoundException("Invalid confirmation token: " + confirmationToken));
    }
}
