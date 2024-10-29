package org.example.foodru_microservice.service.meal;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.model.dto.MealDto;
import org.example.foodru_microservice.model.dto.MealWithIngredientDto;
import org.example.foodru_microservice.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.foodru_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static org.example.foodru_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;

    @Override
    public List<MealDto> getAllMeals() {
        return requireNonEmptyCollection(
                mealRepository.findAll()
                        .stream()
                        .map(mealMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public MealDto getMealById(Integer id) {
        return mealMapper.toDto(
                requirePresentEntity(
                        mealRepository.findById(id)
                )
        );
    }

    @Override
    public MealWithIngredientDto getMealsIngredients(Integer id) {

        return mealMapper.toDtoWithIngredients(
                requirePresentEntity(
                        mealRepository.getMealWithIngredients(id)
                )
        );

    }

}
