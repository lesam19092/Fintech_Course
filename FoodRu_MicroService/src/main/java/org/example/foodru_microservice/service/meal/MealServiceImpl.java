package org.example.foodru_microservice.service.meal;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.repository.MealRepository;
import org.example.foodru_microservice.service.kafka.KafkaConsumer;
import org.example.foodru_microservice.service.kafka.KafkaProducer;
import org.example.foodru_microservice.service.kafka.dto.ListIngredientDto;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.example.foodru_microservice.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final KafkaProducer kafkaProducer;
    private final KafkaConsumer kafkaConsumer;

    private final Map<Long, PaymentReceiptResponse> cachedResponses = new ConcurrentHashMap<>();


    @Override
    public List<MealDto> getAllMeals() {
        return mealRepository.findAll()
                .stream()
                .map(mealMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), EntityUtils::requireNonEmptyCollection));
    }

    @Override
    public MealDto getMealDtoById(Long id) {
        return mealRepository.findById(id)
                .map(mealMapper::toDto)
                .orElseThrow(() -> new EntitySearchException("Meal not found"));
    }

    @Override
    public Meal getMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntitySearchException("Meal not found"));
    }

    @Override
    public MealWithIngredientDto getMealsIngredients(Long id) {
        return mealRepository.getMealWithIngredients(id)
                .map(mealMapper::toDtoWithIngredients)
                .orElseThrow(() -> new EntitySearchException("Meal not found"));
    }


    @Override
    public PaymentReceiptResponse getCheapestMealsIngredients(Long id) {

        PaymentReceiptResponse cachedResponse = cachedResponses.get(id);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        MealWithIngredientDto mealWithIngredientDto = getMealsIngredients(id);

        ListIngredientDto listIngredientDto = new ListIngredientDto();
        listIngredientDto.setIngredientDtoList(mealWithIngredientDto.getIngredients());
        kafkaProducer.sendMessage(listIngredientDto);

        PaymentReceiptResponse response = kafkaConsumer.getResponse();
        cachedResponses.put(id, response);
        return response;
    }

    @Override
    public PaymentReceiptResponse getCachedResponse(Long mealId) {
        PaymentReceiptResponse cachedResponse = cachedResponses.get(mealId);
        if (cachedResponse == null) {
            throw new EntitySearchException("Cached response not found for meal ID: " + mealId);
        }
        return cachedResponse;
    }


}