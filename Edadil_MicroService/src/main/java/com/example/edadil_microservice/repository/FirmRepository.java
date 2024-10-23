package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FirmRepository extends JpaRepository<Firm, Integer> {


    @Query("SELECT f FROM Firm f JOIN FETCH f.products WHERE f.id = :firmId")
    Optional<Firm> findById(Integer firmId);
}