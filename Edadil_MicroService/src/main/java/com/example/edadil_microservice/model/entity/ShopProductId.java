package com.example.edadil_microservice.model.entity;

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
public class ShopProductId implements Serializable {
    @NotNull
    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShopProductId entity = (ShopProductId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.shopId, entity.shopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, shopId);
    }

}

