package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.controller.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> findAllCompanies();

    CompanyDto findCompanyById(Integer companyId);

    List<CompanyDto> findCompaniesSellingFirmProducts(Integer firmId);

    CompanyDto findCompanySellingFirmProductsById(Integer firmId, Integer companyId);


}
