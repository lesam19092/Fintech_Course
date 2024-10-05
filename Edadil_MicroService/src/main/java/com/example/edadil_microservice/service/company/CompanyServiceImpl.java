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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

// TODO отрефакторить сервис
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final ShopRepository shopRepository;


    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return getCollectionOrElseThrowException(companies);
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            return company.get();
        }
        throw new NoSuchElementException("Company with id " + companyId + " not found");
    }

    @Override
    public Set<ShopResponse> getCompanyShops(Integer companyId) {
        Company company = getCompanyById(companyId);
        Set<Shop> shops = company.getShops();
        Set<ShopResponse> response = ShopResponseMapper.mapShopsToShopResponses(shops);

        return getCollectionOrElseThrowException(response);
    }

    @Override
    public Set<ShopResponse> getCompanyShopsByCity(Integer companyId, String city) {
        Company company = getCompanyById(companyId);
        Set<Shop> shops = shopRepository.findShopsByNameOfCompanyAndCity(company, city);
        Set<ShopResponse> response = ShopResponseMapper
                .mapShopsToShopResponses(shops)
                .stream()
                .collect(Collectors.toSet());

        return getCollectionOrElseThrowException(response);

    }

    @Override
    public ShopResponse getCompanyShopsByCityAndShopId(Integer companyId, String city, Integer shopId) {

        Set<ShopResponse> shops = getCompanyShopsByCity(companyId, city);

        Optional<ShopResponse> shop = shops.stream()
                .filter(s -> s.getId() == (shopId))
                .findFirst();
        if (shop.isPresent()) {
            return shop.get();
        }
        throw new NoSuchElementException("Shop with id " + shopId + " not found");

    }


    @Override
    public ShopProductResponse getCompanyShopProducts(Integer companyId, String city, Integer shopId) {


        Optional<Shop> shop = shopRepository.findShopByNameOfCompanyAndIdAndCity(getCompanyById(companyId), shopId, city);

        if (shop.isPresent()) {
            Set<ProductResponse> set = ShopProductResponseMapper.mapProductsToProductResponses(shop.get().getShopproducts());
            ShopProductResponse shopProductResponse = ShopProductResponse.builder()
                    .shop(ShopResponseMapper.mapShopToShopResponse(shop.get()))
                    .products(set)
                    .build();
            return shopProductResponse;

        }
        throw new NoSuchElementException("Shop with id " + shopId + " not found");
    }

    private static <T extends Collection<?>> T getCollectionOrElseThrowException(T collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        throw new NoSuchElementException("Empty collection");
    }
}


//todo refactoring