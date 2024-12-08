package org.example.foodru_microservice.service.user;


import org.example.foodru_microservice.IntegrationTestBase;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.handler.exception.EntitySearchException;
import org.example.foodru_microservice.handler.exception.MenuNotFoundException;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.repository.UserRepository;
import org.example.foodru_microservice.service.jwt.JwtTokenService;
import org.example.foodru_microservice.service.meal.MealService;
import org.example.foodru_microservice.service.menu.MenuService;
import org.example.foodru_microservice.service.user_meal.UserMealService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends IntegrationTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealService mealService;

    @Autowired
    private UserMealService userMealService;

    @Autowired
    private MenuService menuService;


    @InjectMocks
    private UserServiceImpl mockUserService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @Test
    void saveUser_UserAlreadyExists_NoSave() {
        String jwtToken = "sample.jwt.token";
        String username = "John Doe";

        when(jwtTokenService.getUsername(jwtToken)).thenReturn(username);
        when(mockUserRepository.existsByName(username)).thenReturn(true);

        mockUserService.saveUser(jwtToken);

        verify(mockUserRepository, times(1)).existsByName(username);
        verify(mockUserRepository, never()).save(any(User.class));
    }

    @Test
    void createUserFromToken_ValidJwtToken() {
        String jwtToken = "sample.jwt.token";
        String username = "Jane Smith";
        String email = "jane.smith@example.com";
        String role = "ROLE_USER";

        when(jwtTokenService.getUsername(jwtToken)).thenReturn(username);
        when(jwtTokenService.getEmail(jwtToken)).thenReturn(email);
        when(jwtTokenService.getRole(jwtToken)).thenReturn(role);

        User createdUser = mockUserService.createUserFromToken(jwtToken);

        assertThat(createdUser.getName()).isEqualTo(username);
        assertThat(createdUser.getEmail()).isEqualTo(email);
        assertThat(createdUser.getRole().name()).isEqualTo(role);
    }


    @Test
    void testAddMeal() {
        userService.addMeal(2L, "John Doe");

        List<MealDto> meals = userMealService.getAllMeals(userRepository.findByName("John Doe").orElseThrow());
        assertThat(meals).hasSize(2);
        assertThat(meals).extracting("name").contains("Pancakes");
    }

    @Test
    void testAddMeal_UserNotFound() {
        assertThatThrownBy(() -> userService.addMeal(1L, "Unknown User"))
                .isInstanceOf(EntitySearchException.class)
                .hasMessage("User not found");
    }

    @Test
    void testGetAllMeals() {
        List<MealDto> meals = userService.getAllMeals("Jane Smith");

        assertThat(meals).hasSize(2);
        assertThat(meals.get(0).getName()).isEqualTo("Salad");
    }

    @Test
    void testCreateMenu() {
        userService.createMenu("John Doe", "Dinner Menu");

        assertThat(menuService.userHasMenu(userRepository.findByName("John Doe").orElseThrow(), "Dinner Menu")).isTrue();
    }

    @Test
    void testAddMealToMenu() {
        userService.addMealToMenu(3L, "Breakfast Menu", "John Doe");

        List<MenuDto> meals = menuService.getMenusByUsername("John Doe");
        assertThat(meals).hasSize(2);
    }

    @Test
    void testAddMealToMenu_MenuNotFound() {
        assertThatThrownBy(() -> userService.addMealToMenu(1L, "Unknown Menu", "John Doe"))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Menu not found for user: John Doe");
    }

    @Test
    void testGetUserByName() {
        User user = userService.getUserByName("Jane Smith");

        assertThat(user.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(user.getRole().name()).isEqualTo("ROLE_USER");
    }

    @Test
    void testGetUserByName_NotFound() {
        assertThatThrownBy(() -> userService.getUserByName("Unknown User"))
                .isInstanceOf(EntitySearchException.class)
                .hasMessage("User not found");
    }
}
