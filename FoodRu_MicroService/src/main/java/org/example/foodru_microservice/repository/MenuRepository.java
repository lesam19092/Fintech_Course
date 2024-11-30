package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.Meal;
import org.example.foodru_microservice.model.entity.Menu;
import org.example.foodru_microservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByUserAndName(User user, String menuName);

    @Query("SELECT m FROM Menu m JOIN FETCH m.user u WHERE u.name = :username")
    List<Menu> findMenuByUserName(String username);


    @Query("SELECT mm.meal FROM MenuMeal mm WHERE mm.menu.id = :menuId")
    List<Meal> findMealsByMenuId(Long menuId);


}