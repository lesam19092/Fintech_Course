package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.TokenFoodru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenFoodruRepository extends JpaRepository<TokenFoodru, Integer> {

    @Query("""
            select t from TokenFoodru t join fetch t.user
            where t.user.id = :userId and t.isLoggedOut = false
            """)
    List<TokenFoodru> findAllAccessTokensByUser(Integer userId);

    Optional<TokenFoodru> findByAccessToken(String token);
}