package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
import com.example.edadil_microservice.mapper.ShopResponseMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.repository.FirmRepository;
import com.example.edadil_microservice.repository.ShopProductRepository;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;
    private final ShopProductRepository shopProductRepository;
    private final CompanyService companyService;

    @Override
    public List<Firm> findAllFirms() {
        log.info("Fetching all firms");
        return requireNonEmptyCollection(firmRepository.findAll());
    }

    @Override
    public Firm findFirmById(Integer firmId) {
        log.info("Fetching firm with ID: {}", firmId);
        return requirePresentEntity(firmRepository.findById(firmId));
    }

    @Override
    public Set<Product> findProductsByFirmId(Integer firmId) {
        log.info("Fetching products for firm with ID: {}", firmId);
        Firm firm = findFirmById(firmId);
        return requireNonEmptyCollection(firm.getProducts());
    }

    @Override
    public Product findProductByIdAndFirmId(Integer firmId, Integer productId) {
        log.info("Fetching product with ID: {} for firm with ID: {}", productId, firmId);
        Set<Product> products = findProductsByFirmId(firmId);
        Optional<Product> product = products.stream()
                .filter(s -> s.getId().equals(productId))
                .findFirst();
        return requirePresentEntity(product);
    }

    @Override
    public Set<ShopProductResponse> findShopsSellingProduct(Integer firmId, Integer productId) {
        log.info("Fetching shops selling product with ID: {} for firm with ID: {}", productId, firmId);
        Set<ShopProduct> set = shopProductRepository.findShopProductsByProduct_Id(productId);
        Set<ShopProductResponse> shopsWithProduct = set.stream().map(
                        ShopProductResponseMapper::buildShopProductResponseWithSingleProduct)
                .collect(Collectors.toSet());
        return requireNonEmptyCollection(shopsWithProduct);
    }

    @Override
    public Set<Company> findCompaniesSellingFirmProducts(Integer firmId) {
        log.info("Fetching companies selling products for firm with ID: {}", firmId);
        List<ShopProduct> list = shopProductRepository.findAll();
        Set<Company> companies = list.stream()
                .filter(shopProduct -> shopProduct.getProduct().getFirm().getId().equals(firmId))
                .map(shopProduct -> shopProduct.getShop().getNameOfCompany())
                .collect(Collectors.toSet());
        return requireNonEmptyCollection(companies);
    }

    @Override
    public Company findCompanySellingFirmProductsById(Integer firmId, Integer companyId) {
        log.info("Fetching company with ID: {} selling products for firm with ID: {}", companyId, firmId);
        Set<Company> companies = findCompaniesSellingFirmProducts(firmId);
        Optional<Company> company = companies.stream()
                .filter(c -> c.getId().equals(companyId))
                .findFirst();
        return requirePresentEntity(company);
    }

    @Override
    public Set<ShopResponse> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId) {
        log.info("Fetching shops in company with ID: {} selling products for firm with ID: {}", companyId, firmId);
        List<ShopProduct> shopProduct = shopProductRepository.findAll();
        Set<ShopResponse> shopResponses = shopProduct.stream()
                .filter(shopProduct1 -> shopProduct1.getProduct().getFirm().getId().equals(firmId))
                .filter(shopProduct1 -> shopProduct1.getShop().getNameOfCompany().getId().equals(companyId))
                .map(shopProduct1 -> ShopResponseMapper.buildShopResponse(shopProduct1.getShop()))
                .collect(Collectors.toSet());
        return requireNonEmptyCollection(shopResponses);
    }

    @Override
    public ShopResponse findShopInCompanyWithFirmProductsById(Integer firmId, Integer companyId, Integer shopId) {
        log.info("Fetching shop with ID: {} in company with ID: {} selling products for firm with ID: {}", shopId, companyId, firmId);
        Set<ShopResponse> shopResponses = findShopsInCompanyWithFirmProducts(firmId, companyId);
        Optional<ShopResponse> company = shopResponses.stream()
                .filter(c -> c.getId() == shopId)
                .findFirst();
        return requirePresentEntity(company);
    }

    @Override
    public ShopProductResponse findProductsInShopByFirmAndCompany(Integer firmId, Integer companyId, Integer shopId) {
        log.info("Fetching products in shop with ID: {} for firm with ID: {} and company with ID: {}", shopId, firmId, companyId);
        ShopResponse shop = findShopInCompanyWithFirmProductsById(firmId, companyId, shopId);
        ShopProductResponse shopProduct = companyService.retrieveShopProducts(companyId, shop.getCity(), shopId);
        shopProduct.setProducts(shopProduct.getProducts().stream().filter(productResponse ->
                productResponse
                        .getFirm()
                        .equals(findFirmById(firmId).getFirmName())).collect(Collectors.toSet()));
        return shopProduct;
    }
}