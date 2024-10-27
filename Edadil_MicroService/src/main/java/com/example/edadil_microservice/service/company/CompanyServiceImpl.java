package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
import com.example.edadil_microservice.mapper.ShopResponseMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.repository.CompanyRepository;
import com.example.edadil_microservice.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ShopService shopService;

    @Override
    public List<Company> findAllCompanies() {
        log.info("Fetching all companies");
        return requireNonEmptyCollection(companyRepository.findAll());
    }

    @Override
    public Company findCompanyById(Integer companyId) {
        log.info("Fetching company with ID: {}", companyId);
        return requirePresentEntity(companyRepository.findById(companyId));
    }

    @Override
    public Set<ShopResponse> findCompanyShops(Integer companyId) {
        log.info("Fetching shops for company with ID: {}", companyId);
        Company company = requirePresentEntity(companyRepository.findByIdWIthShops(companyId));
        return ShopResponseMapper.convertShopsToShopResponses(company.getShops());
    }

    @Override
    public Set<ShopResponse> findCompanyShopsInCity(Integer companyId, String city) {
        log.info("Fetching shops for company with ID: {} in city: {}", companyId, city);
        Set<Shop> shops = shopService.findShopsByCompanyIdAndCity(companyId, city);
        return ShopResponseMapper.convertShopsToShopResponses(shops);
    }

    @Override
    public ShopResponse findCompanyShopInCityById(Integer companyId, String city, Integer shopId) {
        log.info("Fetching shop with ID: {} in city: {} for company with ID: {}", shopId, city, companyId);
        Shop shop = shopService.findShopByCompanyIdAndCityAndId(companyId, city, shopId);
        return ShopResponseMapper.buildShopResponse(shop);
    }

    //todo не пофиксил проблему n+1
    @Override
    public ShopProductResponse retrieveShopProducts(Integer companyId, String city, Integer shopId) {
        log.info("Fetching products for shop with ID: {} in city: {} for company with ID: {}", shopId, city, companyId);
        Shop shop = shopService.findShopProductsByCompanyIdAndCityAndId(companyId, city, shopId);
        log.info("dddddddddddddddddd");

        return ShopProductResponseMapper.buildShopProductResponse(shop);
    }

    @Override
    public ShopProductResponse retrieveShopProducts(Integer companyId, Integer shopId) {
        log.info("Fetching products for shop with ID: {} for company with ID: {}", shopId, companyId);
        Shop shop = shopService.findShopByNameOfCompanyIdAndId(companyId, shopId);

        return ShopProductResponseMapper.buildShopProductResponse(shop);
    }

    //todo отрефакторить пока что нигде не используется
    @Override
    public ShopProductResponse findSpecificProductInShop(Integer companyId, String city, Integer shopId, String name) {
        log.info("Fetching specific product: {} for shop with ID: {} in city: {} for company with ID: {}", name, shopId, city, companyId);
        ShopProductResponse shopProductResponse = retrieveShopProducts(companyId, city, shopId);
        Optional<ProductResponse> product = shopProductResponse.getProducts().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
        shopProductResponse.setProducts(Set.of(requirePresentEntity(product)));
        return shopProductResponse;
    }

    //todo не пофиксил проблему n+1
    @Override
    public List<ShopProductResponse> getAllShopsWithProducts() {
        List<Company> companies = companyRepository.findAllWithShopsAndProducts();
        log.debug("Found {} companies", companies.size());
        List<ShopProductResponse> shopProductResponses = new ArrayList<>();
        companies.forEach(company -> {
            company.getShops().forEach(shop -> {
                log.debug("Processing shop with ID: {} for company with ID: {}", shop.getId(), company.getId());
                ShopProductResponse shopProductResponse = ShopProductResponseMapper.buildShopProductResponse(shop);
                shopProductResponses.add(shopProductResponse);
            });
        });
        log.info("Returning {} shop product responses", shopProductResponses.size());
        return shopProductResponses;
    }
}