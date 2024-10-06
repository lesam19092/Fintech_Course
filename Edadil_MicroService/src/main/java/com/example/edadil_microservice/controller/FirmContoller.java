package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.service.firm.FirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class FirmContoller {

    private final FirmService firmService;


    @GetMapping("/firms")
    public List<Firm> getAllFirms() {
        return firmService.getAllFirms();
    }

    @GetMapping("/firms/{firmId}")
    public Firm getFirmById(@PathVariable Integer firmId) {
        return firmService.getFirmById(firmId);
    }

    @GetMapping("/firms/{firmId}/products")
    public Set<Product> getFirmProducts(@PathVariable Integer firmId) {
        return firmService.getFirmProducts(firmId);
    }

    @GetMapping("/firms/{firmId}/products/{productId}")
    public Product getFirmProductById(@PathVariable Integer firmId, @PathVariable Integer productId) {
        return firmService.getFirmProductById(firmId, productId);
    }

    @GetMapping("/firms/{firmId}/products/{productId}/shops")
    public Set<ShopProductResponse> getShopsWithProduct(@PathVariable Integer firmId, @PathVariable Integer productId) {
        return firmService.getShopsWithProduct(firmId, productId);
    }


    @GetMapping("/firms/{firmId}/company")
    public Set<Company> getCompaniesHavingFirmProducts(@PathVariable Integer firmId) {
        return firmService.getCompaniesHavingFirmProducts(firmId);
    }


    @GetMapping("/firms/{firmId}/company/{companyId}")
    public Company getCompaniesHavingFirmProductsById(@PathVariable Integer firmId, @PathVariable Integer companyId) {
        return firmService.getCompaniesHavingFirmProductsById(firmId, companyId);
    }


    //получение магазинов компаний , у которых есть продукция фирмы

    @GetMapping("/firms/{firmId}/company/{companyId}/shops")
    public Set<ShopResponse> getShopsInCompanyWithFirmProducts(@PathVariable Integer firmId, @PathVariable Integer companyId) {
        return firmService.findShopsInCompanyWithFirmProducts(firmId, companyId);
    }

    //получение магазина компании , у которых есть продукция фирмы

    @GetMapping("/firms/{firmId}/company/{companyId}/shops/{shopId}")
    public ShopResponse getShopsInCompanyWithFirmProductsById(@PathVariable Integer firmId, @PathVariable Integer companyId, @PathVariable Integer shopId) {
        return firmService.findShopsInCompanyWithFirmProductsById(firmId, companyId, shopId);
    }

    //получение продуктов магазина компании , у которых есть продукция фирмы
    @GetMapping("/firms/{firmId}/company/{companyId}/shops/{shopId}/products")
    public ShopProductResponse findFirmProductsInShop(@PathVariable Integer firmId, @PathVariable Integer companyId, @PathVariable Integer shopId) {
        return firmService.getFirmProductsInShop(firmId, companyId, shopId);
    }


}

