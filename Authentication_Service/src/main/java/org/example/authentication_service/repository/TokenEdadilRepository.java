package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.TokenEdadil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenEdadilRepository extends JpaRepository<TokenEdadil, Integer> {

    @Query("""
            select t from TokenEdadil t join fetch t.user
            where t.user.id = :userId and t.isLoggedOut = false
            """)
    List<TokenEdadil> findAllAccessTokensByUser(Integer userId);

    Optional<TokenEdadil> findByAccessToken(String token);
}