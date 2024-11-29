package org.example.foodru_microservice.service.user_meal;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.model.entity.UsersMeal;
import org.example.foodru_microservice.model.entity.UsersMealId;
import org.example.foodru_microservice.repository.UsersMealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.foodru_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
public class UserMealServiceImpl implements UserMealService {

    private final UsersMealRepository usersMealRepository;

    private final MealMapper mealMapper;

    @Override
    public boolean addMeal(Meal meal, User user) {

        UsersMealId usersMealId = new UsersMealId();
        usersMealId.setUserId(user.getId());
        usersMealId.setMealId(meal.getId());

        if (usersMealRepository.existsById(usersMealId)) {
            return false;
        }

        UsersMeal userMeal = new UsersMeal();
        userMeal.setUser(user);
        userMeal.setMeal(meal);
        userMeal.setId(usersMealId);

        usersMealRepository.save(userMeal);
        return true;
    }

    @Override
    public List<MealDto> getAllMeals(User user) {

        List<UsersMeal> usersMeals = usersMealRepository.findByUserId(user.getId());

        return
                requireNonEmptyCollection(
                        usersMeals.stream()
                                .map(UsersMeal::getMeal)
                                .map(mealMapper::toDto)
                                .collect(Collectors.toList())
                );
    }
}
