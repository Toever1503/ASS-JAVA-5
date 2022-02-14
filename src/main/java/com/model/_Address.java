package com.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Setter
@Getter
public class _Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "province_id", nullable = false)
    private Integer provinceId;

    @Column(name = "district_id", nullable = false)
    private Long districtId;

    @Column(name = "ward_id", nullable = false)
    private Long wardId;

    @Column(name = "detail", nullable = false, length = 500)
    private String detail;

    @Column(name = "user_id", nullable = false)
    private Long userId;

}