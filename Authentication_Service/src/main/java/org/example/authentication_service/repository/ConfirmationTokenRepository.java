package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

}