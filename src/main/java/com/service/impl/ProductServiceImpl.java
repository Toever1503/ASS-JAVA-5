package com.service.impl;

import com.model.Product;
import com.repository.ProductRepository;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.repo = productRepository;
    }

    @Override
    public Product findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Product save(Product obj) {
        return repo.saveAndFlush(obj);
    }

    @Override
    public Product deleteById(Long id) {
        Product obj = findById(id);
        repo.delete(obj);
        return obj;
    }

    @Override
    public Page<Product> findAll(int page) {
        return repo.findAll(PageRequest.of(page, 15).withSort(Sort.by("id").descending()));
    }

    @Override
    public Page<Product> findAllByCategory(Long catId, Pageable page) {
        return repo.findAllOfCat(catId, page);
    }

    @Override
    public Long count(Specification specs) {
        return repo.count(specs);
    }


    @Override
    public Page<Product> findAllByTitleLike(String q, Integer page) {
        return repo.findAllByTitleLike(q, PageRequest.of(page, 10));
    }

    @Override
    public List<Product> findTopSales() {
        return repo.findTopSales();
    }
}
