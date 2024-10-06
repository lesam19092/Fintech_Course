package com.example.edadil_microservice.service.firm;


import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
import com.example.edadil_microservice.mapper.ShopResponseMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.repository.CompanyRepository;
import com.example.edadil_microservice.repository.FirmRepository;
import com.example.edadil_microservice.repository.ShopProductRepository;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirmServiceImpl implements FirmService {

    private final FirmRepository firmRepository;

    private final ShopProductRepository shopProductRepository;


    private final CompanyService companyService;


    @Override
    public List<Firm> getAllFirms() {
        return getCollectionOrElseThrowException(firmRepository.findAll());
    }


    @Override
    public Firm getFirmById(Integer firmId) {
        return getEntityOrElseThrowException(firmRepository.findById(firmId));
    }


    @Override
    public Set<Product> getFirmProducts(Integer firmId) {
        Firm firm = getFirmById(firmId);
        return getCollectionOrElseThrowException(firm.getProducts());
    }


    @Override
    public Product getFirmProductById(Integer firmId, Integer productId) {
        Set<Product> products = getFirmProducts(firmId);
        Optional<Product> product = products.stream()
                .filter(s -> s.getId().equals(productId))
                .findFirst();
        return getEntityOrElseThrowException(product);
    }


    @Override
    public Set<ShopProductResponse> getShopsWithProduct(Integer firmId, Integer productId) {

        Set<ShopProduct> set = shopProductRepository.findShopProductsByProduct_Id(productId);

        Set<ShopProductResponse> shopsWithProduct = set.stream().map(
                        ShopProductResponseMapper::mapShopToShopProductResponseWithOneProduct)
                .collect(Collectors.toSet());


        return getCollectionOrElseThrowException(shopsWithProduct);

    }


    @Override
    public Set<Company> getCompaniesHavingFirmProducts(Integer firmId) {


        List<ShopProduct> list = shopProductRepository.findAll();
        Set<Company> companies = list.stream()
                .filter(shopProduct -> shopProduct.getProduct().getFirm().getId().equals(firmId))
                .map(shopProduct -> shopProduct.getShop().getNameOfCompany())
                .collect(Collectors.toSet());


        return getCollectionOrElseThrowException(companies);


    }


    @Override
    public Company getCompaniesHavingFirmProductsById(Integer firmId, Integer companyId) {

        Set<Company> companies = getCompaniesHavingFirmProducts(firmId);
        Optional<Company> company = companies.stream()
                .filter(c -> c.getId().equals(companyId))
                .findFirst();
        return getEntityOrElseThrowException(company);
    }


    @Override
    public Set<ShopResponse> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId) {

        List<ShopProduct> shopProduct = shopProductRepository.findAll();
        Set<ShopResponse> shopResponses = shopProduct.stream()
                .filter(shopProduct1 -> shopProduct1.getProduct().getFirm().getId().equals(firmId))
                .filter(shopProduct1 -> shopProduct1.getShop().getNameOfCompany().getId().equals(companyId))
                .map(shopProduct1 -> ShopResponseMapper.mapShopToShopResponse(shopProduct1.getShop()))
                .collect(Collectors.toSet());

        return getCollectionOrElseThrowException(shopResponses);
    }


    @Override
    public ShopResponse findShopsInCompanyWithFirmProductsById(Integer firmId, Integer companyId, Integer shopId) {
        Set<ShopResponse> shopResponses = findShopsInCompanyWithFirmProducts(firmId, companyId);

        Optional<ShopResponse> company = shopResponses.stream()
                .filter(c -> c.getId() == shopId)
                .findFirst();

        return getEntityOrElseThrowException(company);

    }


    @Override
    public ShopProductResponse getFirmProductsInShop(Integer firmId, Integer companyId, Integer shopId) {

        ShopResponse shop = findShopsInCompanyWithFirmProductsById(firmId, companyId, shopId);

        ShopProductResponse shopProduct = companyService.getCompanyShopProducts(companyId, shop.getCity(), shopId);

        shopProduct.setProducts(shopProduct.getProducts().stream().filter(productResponse ->
                productResponse
                        .getFirm()
                        .equals(getFirmById(firmId).getFirmName())).collect(Collectors.toSet()));


        return shopProduct;

    }


    private static <T extends Collection<?>> T getCollectionOrElseThrowException(T collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        throw new NoSuchElementException("Empty collection");
    }

    private static <E> E getEntityOrElseThrowException(Optional<E> entity) {
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new NoSuchElementException("Entity with id not found");

    }
}
