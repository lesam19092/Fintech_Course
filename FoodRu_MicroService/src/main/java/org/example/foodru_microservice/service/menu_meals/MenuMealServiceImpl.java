package org.example.foodru_microservice.service.menu_meals;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Menu;
import org.example.foodru_microservice.model.entity.MenuMeal;
import org.example.foodru_microservice.model.entity.MenuMealId;
import org.example.foodru_microservice.repository.MenuMealRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuMealServiceImpl implements MenuMealService {

    private final MenuMealRepository menuMealRepository;


    @Override
    public void addMealToMenu(Menu menu, Meal meal) {

        MenuMeal menuMeal = new MenuMeal();
        MenuMealId menuMealId = new MenuMealId();
        menuMealId.setMenuId(menu.getId());
        menuMealId.setMealId(meal.getId());

        menuMeal.setId(menuMealId);
        menuMeal.setMenu(menu);
        menuMeal.setMeal(meal);

        menuMealRepository.save(menuMeal);
    }
}
