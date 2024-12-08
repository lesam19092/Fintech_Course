
package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.controller.dto.IngredientDto;
import com.example.edadil_microservice.controller.dto.ProductDetailsDto;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.handler.exception.EmptyResultException;
import com.example.edadil_microservice.service.kafka.KafkaProducer;
import com.example.edadil_microservice.service.kafka.response.IngredientResponse;
import com.example.edadil_microservice.service.kafka.response.PaymentReceiptResponse;
import com.example.edadil_microservice.service.shop_product.ShopProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {



    private final ShopProductService shopProductService;

    private final KafkaProducer kafkaProducer;


    @Override
    public List<PaymentReceiptResponse> generatePaymentReceipt(List<IngredientDto> response) {
        List<ShopProductDto> allShopsProducts = shopProductService.getAllShopsWithProducts();
        List<PaymentReceiptResponse> payments = allShopsProducts.stream()
                .map(shop -> createPayment(shop, response))
                .toList();
        log.info("Generated {} payment receipts", payments.size());
        return requireNonEmptyCollection(payments);
    }


    @Override
    public List<PaymentReceiptResponse> getPaymentsWithOutMissingIngredients(List<IngredientDto> requests) {
        List<ShopProductDto> allShopsProducts = shopProductService.getAllShopsWithProducts();
        List<PaymentReceiptResponse> ingredientResponses = allShopsProducts.stream()
                .map(shop -> createPayment(shop, requests))
                .toList();
        log.info("Generated {} ingredient responses without missing ingredients", ingredientResponses.size());
        return ingredientResponses;
    }

    @Override
    @Transactional
    public PaymentReceiptResponse getTheCheapestPayments(List<IngredientDto> requests) {
        List<PaymentReceiptResponse> ingredientResponses = getPaymentsWithOutMissingIngredients(requests);
        PaymentReceiptResponse cheapestPayment = findCheapestPayment(ingredientResponses);
        kafkaProducer.sendMessage(cheapestPayment);
        return cheapestPayment;
    }

    private PaymentReceiptResponse findCheapestPayment(List<PaymentReceiptResponse> responses) {
        return responses.stream()
                .filter(response -> response.getCost() > 0)
                .min(Comparator.comparingDouble(PaymentReceiptResponse::getCost))
                .orElseThrow(() -> new EmptyResultException("No valid payments found"));
    }


    private PaymentReceiptResponse createPayment(ShopProductDto shop, List<IngredientDto> ingredients) {
        log.debug("Creating payment receipt for shop ID: {}", shop.getShop().getId());
        PaymentReceiptResponse paymentReceipt = initializePaymentReceipt(shop);
        ingredients.forEach(ingredient -> processIngredient(ingredient, shop, paymentReceipt));
        return paymentReceipt;
    }

    private PaymentReceiptResponse initializePaymentReceipt(ShopProductDto shop) {
        return PaymentReceiptResponse.builder()
                .address(shop.getShop().getCity() + ", " + shop.getShop().getAddress())
                .companyName(shop.getShop().getCompanyName())
                .ingredients(new ArrayList<>())
                .missingIngredients(new ArrayList<>())
                .cost(0.0)
                .build();
    }


    private void processIngredient(IngredientDto ingredient, ShopProductDto shop, PaymentReceiptResponse paymentReceipt) {
        shop.getProducts().stream()
                .filter(product -> isMatchingProduct(product, ingredient))
                .findFirst()
                .ifPresentOrElse(
                        product -> addIngredientToReceipt(product, ingredient, paymentReceipt),
                        () -> addMissingIngredientToReceipt(ingredient, paymentReceipt)
                );
    }

    private boolean isMatchingProduct(ProductDetailsDto product, IngredientDto ingredient) {
        return product.getName().equalsIgnoreCase(ingredient.getName())
                && product.getCount() >= ingredient.getMeasure();
    }

    private void addIngredientToReceipt(ProductDetailsDto product, IngredientDto ingredient, PaymentReceiptResponse paymentReceipt) {
        log.debug("Found matching product: {}", product.getName());

        IngredientResponse ingredientResponse = createIngredientResponse(ingredient, product);
        paymentReceipt.addIngredient(ingredientResponse);
        paymentReceipt.setCost(product.getPrice(), ingredient.getMeasure());
    }

    private void addMissingIngredientToReceipt(IngredientDto ingredient, PaymentReceiptResponse paymentReceipt) {
        log.warn("Missing ingredient: {}", ingredient.getName());
        paymentReceipt.addMissingIngredient(ingredient.getName());
    }

    private IngredientResponse createIngredientResponse(IngredientDto item, ProductDetailsDto product) {
        return new IngredientResponse(item.getName(),
                item.getMeasure(),
                product.getFirm(),
                product.getPrice() * item.getMeasure());
    }

}


