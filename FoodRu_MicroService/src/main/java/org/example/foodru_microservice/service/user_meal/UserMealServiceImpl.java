package org.example.foodru_microservice.service.user_meal;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.handler.exception.MealAlreadyExistsException;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.model.entity.UsersMeal;
import org.example.foodru_microservice.model.entity.UsersMealId;
import org.example.foodru_microservice.repository.UsersMealRepository;
import org.example.foodru_microservice.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMealServiceImpl implements UserMealService {

    private final UsersMealRepository usersMealRepository;

    private final MealMapper mealMapper;

    @Override
    public void addMeal(Meal meal, User user) {
        UsersMealId usersMealId = new UsersMealId(user.getId(), meal.getId());
        if (usersMealRepository.existsById(usersMealId)) {
            throw new MealAlreadyExistsException("Meal already exists for user");
        }
        UsersMeal userMeal = new UsersMeal(usersMealId, user, meal);
        usersMealRepository.save(userMeal);
    }

    @Override
    public List<MealDto> getAllMeals(User user) {
        List<UsersMeal> usersMeals = usersMealRepository.findByUserId(user.getId());
        return usersMeals.stream()
                .map(UsersMeal::getMeal)
                .map(mealMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), EntityUtils::requireNonEmptyCollection));
    }
}
