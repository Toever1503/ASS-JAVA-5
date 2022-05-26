package com.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String catName;

    @OneToMany(mappedBy = "product_Cat", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Product> car_Products;

    public Object toJson() {
        this.setCar_Products(Collections.EMPTY_SET);
        return new JSONObject(this);
    }
}