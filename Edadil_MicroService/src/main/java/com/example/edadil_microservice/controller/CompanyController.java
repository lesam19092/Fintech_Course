package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/api/v1-edadil") //todo придумать
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping("/companies")
    public List<Company> getAllCompany() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/companies/{companyId}")
    public Company getCompanyById(@PathVariable Integer companyId) {
        return companyService.getCompanyById(companyId);
    }


    @GetMapping("/companies/{companyId}/shops")
    public Set<ShopResponse> getCompanyShops(@PathVariable Integer companyId) {
        return companyService.getCompanyShops(companyId);
    }

    @GetMapping("/companies/{companyId}/shops/{city}")
    public Set<ShopResponse> getCompanyShopsByCity(@PathVariable Integer companyId, @PathVariable String city) {
        return companyService.getCompanyShopsByCity(companyId, city);
    }

    @GetMapping("/companies/{companyId}/shops/{сity}/{shopId}")
    public ShopResponse getCompanyShopFromCityById(@PathVariable Integer companyId, @PathVariable String сity, @PathVariable Integer shopId) {
        return companyService.getCompanyShopsByCityAndShopId(companyId, сity, shopId);
    }


    @GetMapping("/companies/{companyId}/shops/{сity}/{shopId}/products")
    public ShopProductResponse getCompanyShopProducts(@PathVariable Integer companyId, @PathVariable String сity, @PathVariable Integer shopId) {
        return companyService.getCompanyShopProducts(companyId, сity, shopId);
    }


}
