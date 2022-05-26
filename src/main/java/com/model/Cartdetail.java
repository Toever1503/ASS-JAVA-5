package com.model;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity
@Table(name = "cartdetail")
@Data
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable
public class Cartdetail {
    @EmbeddedId
    private CartdetailId id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}