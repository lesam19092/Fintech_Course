package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping("/companies")
    public List<Company> getAllCompany() {
        return companyService.findAllCompanies();
    }

    @GetMapping("/companies/{companyId}")
    public Company getCompanyById(@PathVariable Integer companyId) {
        return companyService.findCompanyById(companyId);
    }


    @GetMapping("/companies/{companyId}/shops")
    public Set<ShopResponse> getCompanyShops(@PathVariable Integer companyId) {
        return companyService.findCompanyShops(companyId);
    }

    @GetMapping("/companies/{companyId}/shops/{city}")
    public Set<ShopResponse> getCompanyShopsByCity(@PathVariable Integer companyId, @PathVariable String city) {
        return companyService.findCompanyShopsInCity(companyId, city);
    }

    @GetMapping("/companies/{companyId}/shops/{city}/{shopId}")
    public ShopResponse getCompanyShopFromCityById(@PathVariable Integer companyId, @PathVariable String city, @PathVariable Integer shopId) {
        return companyService.findCompanyShopInCityById(companyId, city, shopId);
    }


    @GetMapping("/companies/{companyId}/shops/{city}/{shopId}/products")
    public ShopProductResponse getCompanyShopProducts(@PathVariable Integer companyId, @PathVariable String city, @PathVariable Integer shopId) {
        return companyService.retrieveShopProducts(companyId, city, shopId);
    }
    

}
