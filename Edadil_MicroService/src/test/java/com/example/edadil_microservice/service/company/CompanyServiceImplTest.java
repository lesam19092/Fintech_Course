package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.IntegrationTestBase;
import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.handler.exception.NoCompaniesSellingFirmProductsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyServiceImplTest extends IntegrationTestBase {

    @Autowired
    private CompanyService companyService;

    @Test
    void findAllCompanies_whenCompaniesExist_returnsCompanies() {
        List<CompanyDto> companies = companyService.findAllCompanies();
        assertEquals(2, companies.size(), "Should return all companies from the database");
    }

    @Test
    void findCompanyById_whenCompanyExists_returnsCompany() {
        Long companyId = 1L;
        CompanyDto company = companyService.findCompanyById(companyId);
        assertEquals("Пятерочка", company.getCompanyName(), "Company name should match");
    }

    @Test
    void findCompanyById_whenCompanyDoesNotExist_throwsException() {
        Long nonExistentCompanyId = 999L;

        assertThrows(EntityNotFoundException.class,
                () -> companyService.findCompanyById(nonExistentCompanyId),
                "Expected EntityNotFoundException for a non-existent company ID"
        );
    }

    @Test
    void findCompaniesSellingFirmProducts_whenFirmProductsExist_returnsCompanies() {
        Long firmId = 1L;
        List<CompanyDto> companies = companyService.findCompaniesSellingFirmProducts(firmId);

        assertEquals(2, companies.size(), "Should return companies selling products from the given firm");
    }

    @Test
    void findCompaniesSellingFirmProducts_whenFirmProductsDoNotExist_throwsException() {
        Long nonExistentFirmId = 999L;

        assertThrows(NoCompaniesSellingFirmProductsException.class,
                () -> companyService.findCompaniesSellingFirmProducts(nonExistentFirmId),
                "Expected NoCompaniesSellingFirmProductsException for a firm with no products"
        );
    }

    @Test
    void findCompanySellingFirmProductsById_whenCompanyAndFirmExist_returnsCompany() {
        Long firmId = 1L;
        Long companyId = 1L;
        CompanyDto company = companyService.findCompanySellingFirmProductsById(firmId, companyId);

        assertNotNull(company, "Company should not be null");
        assertEquals("Пятерочка", company.getCompanyName(), "Company name should match");
    }

    @Test
    void findCompanySellingFirmProductsById_whenCompanyDoesNotSellFirmProducts_throwsException() {
        Long firmId = 1L;
        Long companyId = 999L;

        assertThrows(EntityNotFoundException.class,
                () -> companyService.findCompanySellingFirmProductsById(firmId, companyId),
                "Expected EntityNotFoundException for a company not selling firm products"
        );
    }
}
