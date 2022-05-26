package com.model;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "totalPrice", nullable = false)
    private Integer totalPrice;

    @Column(name = "deliveryFee", nullable = false)
    private Integer deliveryFee;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "addressId", nullable = false)
    private Long address;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "status", nullable = false)
    private String status;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "dateCreate", nullable = false)
    private Date dateCreate;

}