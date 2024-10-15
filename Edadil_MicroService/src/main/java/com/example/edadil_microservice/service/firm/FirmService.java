package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;

import java.util.List;
import java.util.Set;

public interface FirmService {

    List<Firm> findAllFirms();

    Firm findFirmById(Integer firmId);

    Set<Product> findProductsByFirmId(Integer firmId);

    Product findProductByIdAndFirmId(Integer firmId, Integer productId);

    Set<ShopProductResponse> findShopsSellingProduct(Integer firmId, Integer productId);

    Set<Company> findCompaniesSellingFirmProducts(Integer firmId);

    Company findCompanySellingFirmProductsById(Integer firmId, Integer companyId);

    Set<ShopResponse> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId);

    ShopResponse findShopInCompanyWithFirmProductsById(Integer firmId, Integer companyId, Integer shopId);

    ShopProductResponse findProductsInShopByFirmAndCompany(Integer firmId, Integer companyId, Integer shopId);
}
