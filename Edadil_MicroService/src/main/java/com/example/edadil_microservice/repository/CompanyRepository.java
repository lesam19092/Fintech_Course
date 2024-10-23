package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("SELECT c FROM Company c JOIN FETCH c.shops WHERE c.id = :companyId")
    Optional<Company> findByIdWIthShops(Integer companyId);
}