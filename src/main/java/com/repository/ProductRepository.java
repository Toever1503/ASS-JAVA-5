package com.repository;

import com.model.Category;
import com.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "select p from Product p where p.product_Cat.id=:cartId")
    public Page<Product> findAllOfCat(@Param("cartId") Long id, Pageable page);

    public Page<Product> findAllByTitleLike(String q, Pageable page);

    @Query(value = "SELECT * FROM product as p\n" +
            "LEFT JOIN orderdetail as od on od.productId = p.id\n" +
            "ORDER BY SUM(od.productId) DESC\n" +
            "LIMIT 0, 10", nativeQuery = true)
    public List<Product> findTopSales();
}