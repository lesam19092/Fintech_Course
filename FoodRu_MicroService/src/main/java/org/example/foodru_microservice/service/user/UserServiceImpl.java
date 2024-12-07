package org.example.foodru_microservice.service.user;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.handler.exception.MenuNotFoundException;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Role;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.repository.UserRepository;
import org.example.foodru_microservice.service.jwt.JwtTokenService;
import org.example.foodru_microservice.service.meal.MealService;
import org.example.foodru_microservice.service.menu.MenuService;
import org.example.foodru_microservice.service.user_meal.UserMealService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MealService mealService;

    private final UserMealService userMealService;

    private final MenuService menuService;

    private final JwtTokenService jwtTokenService;


    @Override
    public void saveUser(String jwtToken) {
        String username = jwtTokenService.getUsername(jwtToken);
        if (userRepository.existsByName(username)) {
            return;
        }
        User user = createUserFromToken(jwtToken);
        userRepository.save(user);
    }

    @Override
    public void addMeal(Long id, String username) {
        User user = getUserByName(username);
        Meal meal = mealService.getMealById(id);
        userMealService.addMeal(meal, user);
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByName(username).
                orElseThrow(() -> new EntitySearchException("User not found"));
    }

    @Override
    public List<MealDto> getAllMeals(String username) {
        return userMealService.getAllMeals(getUserByName(username));
    }

    @Override
    public void createMenu(String username, String menuName) {
        menuService.createMenu(getUserByName(username), menuName);
    }

    @Override
    public void addMealToMenu(Long mealId, String menuName, String userName) {
        User user = getUserByName(userName);
        if (!menuService.userHasMenu(user, menuName)) {
            throw new MenuNotFoundException("Menu not found for user: " + userName);
        }
        Meal meal = mealService.getMealById(mealId);
        menuService.addMealToMenu(meal, menuName, user);
    }


    protected User createUserFromToken(String jwtToken) {
        User user = new User();
        user.setName(jwtTokenService.getUsername(jwtToken));
        user.setRole(Role.valueOf(jwtTokenService.getRole(jwtToken)));
        user.setEmail(jwtTokenService.getEmail(jwtToken));
        return user;
    }
}
