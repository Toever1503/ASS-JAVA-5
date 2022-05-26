package com.model;


import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class _Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "province_id", nullable = false)
    private Integer provinceId;

    @Column(name = "district_id", nullable = false)
    private Integer districtId;

    @Column(name = "ward_id", nullable = false)
    private Integer wardId;

    @Column(name = "detail", nullable = false, length = 500)
    private String detail;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}