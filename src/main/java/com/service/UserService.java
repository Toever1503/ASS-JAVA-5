package com.service;

import com.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface UserService {
    User findById(Long id);

    User findByEmailOrUsername(String email, String username);

    User save(User obj);

    User deleteById(Long id);

    List<User> findAll(Specification specs, int page);

    Long count(Specification specs);

    List<User> searchByUser(User u);
}
