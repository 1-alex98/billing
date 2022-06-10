package com.billing.app.domain.types.core.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class ProductType{
    @Id
    @Nullable
    private String id;
    private String name;
    private String description;
    private double valueAddedTax;
    private double customs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductType that = (ProductType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
