package com.service;

import com.model.Category;
import com.model.Product;
import com.repository.specification.model.ProductFilter;
import org.eclipse.sisu.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {
    Product findById(Long id);

    Product save(Product obj);

    Product deleteById(Long id);

    Page<Product> findAll(int page);

    Page<Product> findAllByCategory(Long cartId, Pageable size);

    Long count(Specification specs);

    Page<Product> findAllByTitleLike(String q, Integer page);

    List<Product> findTopSales();

}
