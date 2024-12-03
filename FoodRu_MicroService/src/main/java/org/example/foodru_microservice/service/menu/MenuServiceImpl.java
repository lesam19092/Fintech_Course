package org.example.foodru_microservice.service.menu;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.handler.exception.MenuAlreadyExistsException;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.mapper.MenuMapper;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Menu;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.repository.MenuRepository;
import org.example.foodru_microservice.service.menu_meals.MenuMealService;
import org.example.foodru_microservice.service.user_meal.UserMealService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final MealMapper mealMapper;
    private final UserMealService userMealService;
    private final MenuMealService menuMealService;


    @Override
    public void createMenu(User user, String menuName) {
        if (userHasMenu(user, menuName)) {
            throw new MenuAlreadyExistsException("User already has a menu with this name.");
        }
        Menu menu = Menu.builder().name(menuName).user(user).build();
        menuRepository.save(menu);
    }

    @Override
    public List<MenuDto> getMenusByUsername(String username) {
        List<Menu> usersMenus = getUsersMenus(username);
        return usersMenus.stream()
                .map(menuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealDto> getMealsByMenuId(Long id) {
        List<Meal> meals = getMealsByMenuIdOrThrow(id);
        return meals.stream()
                .map(mealMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean userHasMenu(User user, String menuName) {
        return menuRepository.existsByUserAndName(user, menuName);
    }


    @Override
    public void addMealToMenu(Meal meal, String menuName, User user) {
        Menu menu = findMenuByUserAndName(user, menuName);
        List<Meal> meals = menuRepository.findMealsByMenuId(menu.getId());
        if (meals.contains(meal)) {
            throw new EntitySearchException("Meal already exists in the menu.");
        }
        userMealService.addMeal(meal, user);
        menuMealService.addMealToMenu(menu, meal);
    }


    private Menu findMenuByUserAndName(User user, String menuName) {
        return menuRepository.findByMenuByUserAndName(user, menuName)
                .orElseThrow(() -> new EntitySearchException("Menu not found"));
    }

    private List<Menu> getUsersMenus(String username) {
        return Optional.ofNullable(menuRepository.findMenusByUserName(username))
                .filter(menus -> !menus.isEmpty())
                .orElseThrow(() -> new EntitySearchException("Menus not found. Please create a menu."));
    }

    private List<Meal> getMealsByMenuIdOrThrow(Long id) {
        return Optional.ofNullable(menuRepository.findMealsByMenuId(id))
                .filter(mealList -> !mealList.isEmpty())
                .orElseThrow(() -> new EntitySearchException("Meals not found. Please add meals to the menu."));
    }


}
