package com.model;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class OrderdetailId implements Serializable {
    private static final long serialVersionUID = 4975396938035032705L;
    @Column(name = "orderId", nullable = false)
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product productId;

    @Override
    public int hashCode() {
        return Objects.hash(productId, orderId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderdetailId entity = (OrderdetailId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.orderId, entity.orderId);
    }
}