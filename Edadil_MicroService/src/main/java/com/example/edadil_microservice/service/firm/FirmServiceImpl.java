package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
import com.example.edadil_microservice.mapper.ShopResponseMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.repository.CompanyRepository;
import com.example.edadil_microservice.repository.FirmRepository;
import com.example.edadil_microservice.repository.ProductRepository;
import com.example.edadil_microservice.repository.ShopProductRepository;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FirmServiceImpl implements FirmService {


    private final FirmRepository firmRepository;
    private final ShopProductRepository shopProductRepository;
    private final ProductRepository productRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @Override
    public List<Firm> findAllFirms() {
        log.info("Fetching all firms");
        return requireNonEmptyCollection(firmRepository.findAll());
    }

    @Override
    public Firm findFirmById(Integer firmId) {
        log.info("Fetching firm with ID: {}", firmId);
        return requirePresentEntity(
                firmRepository.findById(firmId)
        );
    }

    @Override
    public Set<Product> findProductsByFirmId(Integer firmId) {
        log.info("Fetching products for firm with ID: {}", firmId);
        return requireNonEmptyCollection(
                findFirmById(firmId).getProducts()
        );
    }

    @Override
    public Product findProductByIdAndFirmId(Integer firmId, Integer productId) {
        log.info("Fetching product with ID: {} for firm with ID: {}", productId, firmId);
        return requirePresentEntity(
                productRepository.findProductByIdAndFirmId(firmId, productId)
        );
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
        return requireNonEmptyCollection(
                companyRepository.findCompaniesByFirmId(firmId)
        );
    }

    @Override
    public Company findCompanySellingFirmProductsById(Integer firmId, Integer companyId) {
        log.info("Fetching company with ID: {} selling products for firm with ID: {}", companyId, firmId);
        return requirePresentEntity(
                companyRepository.findByFirmIdAndCompanyId(firmId, companyId)
        );
    }

    @Override
    public Set<ShopResponse> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId) {
        log.info("Fetching shops in company with ID: {} selling products for firm with ID: {}", companyId, firmId);
        return requireNonEmptyCollection(
                shopProductRepository.findShopProductsByFirmIdAndCompanyId(firmId, companyId).stream()
                        .map(shopProduct -> ShopResponseMapper.buildShopResponse(shopProduct.getShop()))
                        .collect(Collectors.toSet())
        );
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
        String firmName = findFirmById(firmId).getFirmName();
        shopProduct.setProducts(shopProduct.getProducts().stream()
                .filter(productResponse -> productResponse.getFirm().equals(firmName))
                .collect(Collectors.toSet()));
        return shopProduct;
    }
}