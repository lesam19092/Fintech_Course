package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("SELECT c FROM Company c JOIN FETCH c.shops WHERE c.id = :companyId")
    Optional<Company> findByIdWIthShops(Integer companyId);

    @Query("SELECT DISTINCT sp.shop.nameOfCompany FROM ShopProduct sp WHERE sp.product.firm.id = :firmId")
    Set<Company> findCompaniesByFirmId(@Param("firmId") Integer firmId);

    @Query("SELECT DISTINCT sp.shop.nameOfCompany FROM ShopProduct sp WHERE sp.product.firm.id = :firmId AND sp.shop.nameOfCompany.id = :companyId")
    Optional<Company> findByFirmIdAndCompanyId(@Param("firmId") Integer firmId, @Param("companyId") Integer companyId);

    @Query("SELECT c FROM Company c JOIN FETCH c.shops s JOIN FETCH s.shopproducts sp JOIN FETCH sp.product")
    List<Company> findAllWithShopsAndProducts();
}