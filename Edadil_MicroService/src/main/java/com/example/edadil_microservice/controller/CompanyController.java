package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping("/companies")
    public List<CompanyDto> getAllCompany() {
        return companyService.findAllCompanies();
    }

    @GetMapping("/companies/{companyId}")
    public CompanyDto getCompanyById(@PathVariable Integer companyId) {
        return companyService.findCompanyById(companyId);
    }

    @GetMapping("/firms/{firmId}/company")
    public List<CompanyDto> getCompaniesHavingFirmProducts(@PathVariable Integer firmId) {
        return companyService.findCompaniesSellingFirmProducts(firmId);
    }

    @GetMapping("/firms/{firmId}/company/{companyId}")
    public CompanyDto getCompaniesHavingFirmProductsById(@PathVariable Integer firmId, @PathVariable Integer companyId) {
        return companyService.findCompanySellingFirmProductsById(firmId, companyId);
    }


}
