package org.example.foodru_microservice.service.menu;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.mapper.MealMapper;
import org.example.foodru_microservice.mapper.MenuMapper;
import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Menu;
import org.example.foodru_microservice.model.entity.Type;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.repository.MenuRepository;
import org.example.foodru_microservice.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.foodru_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final MealMapper mealMapper;
    //todo убрать type из проекта вообще
    private final TypeRepository typeRepository;

    @Override
    public boolean createMenu(User user, String menuName) {
        if (menuRepository.existsByUserAndName(user, menuName)) {
            return false;
        }

        Menu menu = new Menu();
        menu.setUser(user);
        menu.setName(menuName);

        Optional<Type> type = typeRepository.findById(1L);
        menu.setType(type.get());


        //todo убрать type из проекта вообще


        menuRepository.save(menu);
        return true;


    }

    @Override
    public List<MenuDto> getMenusByUsername(String username) {
        List<Menu> usersMenus = requireNonEmptyCollection(menuRepository.findMenuByUserName(username));

        return usersMenus.stream()
                .map(menuMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealDto> getMealsByMenuId(Long id) {

        List<Meal> menus = requireNonEmptyCollection(menuRepository.findMealsByMenuId(id));

        return menus.stream()
                .map(mealMapper::toDto)
                .collect(Collectors.toList());
    }


}
