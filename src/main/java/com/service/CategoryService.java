package com.service;

import com.model.Category;
import com.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);

    Category save(Category obj);

    Category deleteById(Long id);

    Category findByName(String name);

    List<Category> findAll();

}
