package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.model.request.IngredientRequest;
import com.example.edadil_microservice.model.response.IngredientResponse;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    //TODO отрефакторить

    //TODO ЗАПОЛНИТЬ ТАБЛИЦУ ДАННЫМИ

    private final CompanyService companyService;


    //TODO  убрать это в CompanyService
    //добавить функционал где можно будет содержа полный список или частичный


    public List<IngredientResponse> generatePaymentReceipt(List<IngredientRequest> response) {
        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        List<ShopProductResponse> allShopsProducts = companyService.getAllShopsWithProducts();
        log.debug("Retrieved {} shop products", allShopsProducts.size());

        for (ShopProductResponse shop : allShopsProducts) {
            IngredientResponse ingredientResponse = createIngredientResponse(shop, response);
            ingredientResponses.add(ingredientResponse);
            System.out.println(ingredientResponse);
        }
        log.info("Generated {} ingredient responses", ingredientResponses.size());
        return ingredientResponses;
    }

    private IngredientResponse createIngredientResponse(ShopProductResponse shop, List<IngredientRequest> response) {
        log.debug("Creating ingredient response for shop ID: {}", shop.getShop().getId());
        IngredientResponse ingredientResponse = new IngredientResponse();
        ingredientResponse.setAddress(shop.getShop().getAddress() + " " + shop.getShop().getCity());
        ingredientResponse.setCompanyName(shop.getShop().getCompanyName());

        for (IngredientRequest item : response) {
            boolean found = false;
            for (ProductResponse product : shop.getProducts()) {
                if (product.getName().equals(item.getName()) && product.getCount() >= item.getCount()) {
                    log.debug("Found matching product: {} with sufficient count: {}", product.getName(), product.getCount());
                    ingredientResponse.addIngredient(item);
                    ingredientResponse.setCost(product.getPrice(), item.getCount());
                    found = true;
                    break;
                }
            }
            if (!found) {
                log.warn("Missing ingredient: {}", item.getName());
                ingredientResponse.addMissingIngredient(item.getName());
            }
        }
        return ingredientResponse;
    }

}