package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.exception.EmptyResultException;
import com.example.edadil_microservice.model.dto.IngredientDto;
import com.example.edadil_microservice.model.response.IngredientResponse;
import com.example.edadil_microservice.model.response.PaymentReceiptResponse;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {


    //TODO ЗАПОЛНИТЬ ТАБЛИЦУ ДАННЫМИ


    private final CompanyService companyService;


    @Override
    public List<PaymentReceiptResponse> generatePaymentReceipt(List<IngredientDto> response) throws IOException {
        List<ShopProductResponse> allShopsProducts = companyService.getAllShopsWithProducts();

        List<PaymentReceiptResponse> payments = allShopsProducts.stream()
                .map(shop -> createPayment(shop, response))
                .toList();

        log.info("Generated {} ingredient responses", payments.size());

        return requireNonEmptyCollection(payments);
    }


    @Override
    public List<PaymentReceiptResponse> getPaymentsWithOutMissingIngredients(List<IngredientDto> requests) {
        List<ShopProductResponse> allShopsProducts = companyService.getAllShopsWithProducts();

        List<PaymentReceiptResponse> ingredientResponses = allShopsProducts.stream()
                .map(shop -> createPayment(shop, requests))
                .filter(payment -> payment.getMissingIngredients().isEmpty())
                .toList();

        log.info("Generated {} ingredient responses", ingredientResponses.size());
        return requireNonEmptyCollection(ingredientResponses);
    }

    @Override
    public List<PaymentReceiptResponse> getTheCheapestPayments(List<IngredientDto> requests) {

        List<PaymentReceiptResponse> ingredientResponses = getPaymentsWithOutMissingIngredients(requests);

        List<PaymentReceiptResponse> cheapestPayments = ingredientResponses.stream()
                .filter(ir -> ir.getCost() == ingredientResponses.stream()
                        .min(Comparator.comparingDouble(PaymentReceiptResponse::getCost))
                        .orElseThrow(() -> new EmptyResultException("No valid payments found"))
                        .getCost())
                .toList();

        return requireNonEmptyCollection(cheapestPayments);
    }

    //TODO может быть добавить среднюю стоимость для каждой компании


    private PaymentReceiptResponse createPayment(ShopProductResponse shop, List<IngredientDto> response) {
        log.debug("Creating ingredient response for shop ID: {}", shop.getShop().getId());
        PaymentReceiptResponse paymentReceiptResponse = new PaymentReceiptResponse();
        paymentReceiptResponse.setAddress(shop.getShop().getCity() + " , " + shop.getShop().getAddress());
        paymentReceiptResponse.setCompanyName(shop.getShop().getCompanyName());

        response.forEach(item -> processIngredient(item, shop, paymentReceiptResponse));

        return paymentReceiptResponse;
    }

    private void processIngredient(IngredientDto item, ShopProductResponse shop, PaymentReceiptResponse paymentReceiptResponse) {
        boolean found = shop.getProducts().stream()
                .filter(product -> product.getName().equals(item.getName()) && product.getCount() >= item.getMeasure())
                .peek(product -> {
                    log.debug("Found matching product: {} with sufficient count: {}", product.getName(), product.getCount());
                    IngredientResponse ingredient = createIngredientResponse(item, product);
                    paymentReceiptResponse.addIngredient(ingredient);
                    paymentReceiptResponse.setCost(product.getPrice(), item.getMeasure());
                })
                .findFirst()
                .isPresent();

        if (!found) {
            log.warn("Missing ingredient: {}", item.getName());
            paymentReceiptResponse.addMissingIngredient(item.getName());
        }
    }

    private IngredientResponse createIngredientResponse(IngredientDto item, ProductResponse product) {
        return new IngredientResponse(item.getName(),
                        item.getMeasure(),
                        product.getFirm(),
                        product.getPrice() * item.getMeasure());
    }

}

