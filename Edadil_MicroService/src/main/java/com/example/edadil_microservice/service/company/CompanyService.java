package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.controller.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> findAllCompanies();

    CompanyDto findCompanyById(Long companyId);

    List<CompanyDto> findCompaniesSellingFirmProducts(Long firmId);

    CompanyDto findCompanySellingFirmProductsById(Long firmId, Long companyId);


}
