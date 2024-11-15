package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);


    @Query("SELECT u FROM User u JOIN FETCH u.instance WHERE u.name = :username AND u.instance.name = :instanceName")
    Optional<User> findByUsernameAndInstance(String username, String instanceName);


}