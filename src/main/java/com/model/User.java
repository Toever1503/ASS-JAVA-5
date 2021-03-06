package com.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "username", nullable = false)
    @NotNull
    String username;
    @Column(name = "password", nullable = false)
    @NotNull
    String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fullname")
    @NotNull
    private String name;
    @Column(name = "email", nullable = false)
    @NotNull
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id")
    }, inverseJoinColumns = {@JoinColumn(name = "auth_id")})
    private Set<Authority> userAuths;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cartUser")
    private Cart userCart;
}