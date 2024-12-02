package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.СompanyEndpoints.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(GET_ALL_COMPANIES)
    public List<CompanyDto> getAllCompany() {
        return companyService.findAllCompanies();
    }

    @GetMapping(GET_COMPANY_BY_ID)
    public CompanyDto getCompanyById(@PathVariable Integer companyId) {
        return companyService.findCompanyById(companyId);
    }

    @GetMapping(GET_COMPANIES_HAVING_FIRM_PRODUCTS)
    public List<CompanyDto> getCompaniesHavingFirmProducts(@PathVariable Integer firmId) {
        return companyService.findCompaniesSellingFirmProducts(firmId);
    }

    @GetMapping(GET_COMPANIES_HAVING_FIRM_PRODUCTS_BY_ID)
    public CompanyDto getCompaniesHavingFirmProductsById(@PathVariable Integer firmId, @PathVariable Integer companyId) {
        return companyService.findCompanySellingFirmProductsById(firmId, companyId);
    }

}
