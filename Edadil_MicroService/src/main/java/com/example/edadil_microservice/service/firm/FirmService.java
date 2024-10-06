package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;

import java.util.List;
import java.util.Set;

public interface FirmService {

    List<Firm> getAllFirms();

    Firm getFirmById(Integer firmId);

    Set<Product> getFirmProducts(Integer firmId);

    Product getFirmProductById(Integer firmId, Integer productId);

    Set<ShopProductResponse> getShopsWithProduct(Integer firmId, Integer productId);

    Set<Company> getCompaniesHavingFirmProducts(Integer firmId);

    Company getCompaniesHavingFirmProductsById(Integer firmId, Integer companyId);

    Set<ShopResponse> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId);

    ShopResponse findShopsInCompanyWithFirmProductsById(Integer firmId, Integer companyId, Integer shopId);

    ShopProductResponse getFirmProductsInShop(Integer firmId, Integer companyId, Integer shopId);
}
