package com.model;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class CartdetailId implements Serializable {
    private static final long serialVersionUID = -7768865963847106304L;

    @Column(name = "cartId", nullable = false)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product productId;

    @Override
    public int hashCode() {
        return Objects.hash(productId, cartId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartdetailId entity = (CartdetailId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.cartId, entity.cartId);
    }
}