package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    @Min(value = 0)
    @NotNull
    private Integer price;

    @Column(name = "image", nullable = false)
    @NotNull
    private String image;

    @Column(name = "year", nullable = false)
    @NotNull
    private Integer year;

    @Column(name = "season", nullable = false)
    @NotNull
    private String season;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category product_Cat;
}