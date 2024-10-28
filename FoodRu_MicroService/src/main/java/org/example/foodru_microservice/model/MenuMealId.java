package org.example.foodru_microservice.model;

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
public class MenuMealId implements Serializable {
    private static final long serialVersionUID = 1957367655642013788L;
    @NotNull
    @Column(name = "menu_id", nullable = false)
    private Integer menuId;

    @NotNull
    @Column(name = "meal_id", nullable = false)
    private Integer mealId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuMealId entity = (MenuMealId) o;
        return Objects.equals(this.mealId, entity.mealId) &&
                Objects.equals(this.menuId, entity.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, menuId);
    }

}