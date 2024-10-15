package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
import com.example.edadil_microservice.mapper.ShopResponseMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.repository.CompanyRepository;
import com.example.edadil_microservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final ShopRepository shopRepository;


    @Override
    public List<Company> findAllCompanies() {
        return requireNonEmptyCollection(companyRepository.findAll());
    }

    @Override
    public Company findCompanyById(Integer companyId) {
        return requirePresentEntity(companyRepository.findById(companyId));
    }

    @Override
    public Set<ShopResponse> findCompanyShops(Integer companyId) {
        Company company = findCompanyById(companyId);
        return ShopResponseMapper.convertShopsToShopResponses(company.getShops());
    }

    @Override
    public Set<ShopResponse> findCompanyShopsInCity(Integer companyId, String city) {
        Set<Shop> shops = shopRepository.findShopsByNameOfCompanyIdAndCity(companyId, city);
        return ShopResponseMapper.convertShopsToShopResponses(shops);
    }

    @Override
    public ShopResponse findCompanyShopInCityById(Integer companyId, String city, Integer shopId) {
        Set<ShopResponse> shops = findCompanyShopsInCity(companyId, city);
        Optional<ShopResponse> shop = shops.stream()
                .filter(s -> s.getId() == (shopId))
                .findFirst();
        return requirePresentEntity(shop);
    }


    @Override
    public ShopProductResponse retrieveShopProducts(Integer companyId, String city, Integer shopId) {
        Optional<Shop> optionalShop = shopRepository.findShopByNameOfCompanyIdAndIdAndCity(companyId, shopId, city);
        Shop shop = requirePresentEntity(optionalShop);
        return ShopProductResponseMapper
                .buildShopProductResponse(shop);
    }

    @Override
    public ShopProductResponse retrieveShopProducts(Integer companyId, Integer shopId) {
        Optional<Shop> optionalShop = shopRepository.findShopByNameOfCompanyIdAndId(companyId, shopId);
        Shop shop = requirePresentEntity(optionalShop);
        return ShopProductResponseMapper
                .buildShopProductResponse(shop);
    }

    @Override
    public ShopProductResponse findSpecificProductInShop(Integer companyId, String city, Integer shopId, String name) {
        ShopProductResponse shopProductResponse = retrieveShopProducts(companyId, city, shopId);
        Optional<ProductResponse> product = shopProductResponse.getProducts().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
        shopProductResponse.setProducts(Set.of(requirePresentEntity(product)));
        return shopProductResponse;
    }


}
