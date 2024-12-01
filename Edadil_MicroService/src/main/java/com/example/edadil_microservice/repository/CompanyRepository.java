package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {


    @Query("SELECT DISTINCT sp.shop.nameOfCompany FROM ShopProduct sp WHERE sp.product.firm.id = :firmId")
    List<Company> findCompaniesByFirmId(Integer firmId);

    @Query("SELECT DISTINCT sp.shop.nameOfCompany FROM ShopProduct sp WHERE sp.product.firm.id = :firmId AND sp.shop.nameOfCompany.id = :companyId")
    Optional<Company> findByFirmIdAndCompanyId(Integer firmId, Integer companyId);

}