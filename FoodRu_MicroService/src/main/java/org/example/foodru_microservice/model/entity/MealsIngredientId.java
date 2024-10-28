package org.example.foodru_microservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MealsIngredientId implements Serializable {
    private static final long serialVersionUID = 3598559915191657576L;
    @NotNull
    @Column(name = "meal_id", nullable = false)
    private Integer mealId;

    @NotNull
    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MealsIngredientId entity = (MealsIngredientId) o;
        return Objects.equals(this.ingredientId, entity.ingredientId) &&
                Objects.equals(this.mealId, entity.mealId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, mealId);
    }

}