package org.example.foodru_microservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "meals_ingredients")
public class MealsIngredient {
    @EmbeddedId
    private MealsIngredientId id;

    @MapsId("mealId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @MapsId("ingredientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @NotNull
    @Column(name = "measure", nullable = false)
    private Double measure;

}