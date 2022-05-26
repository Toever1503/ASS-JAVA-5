package com.repository;

import com.model.Cartdetail;
import com.model.CartdetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartdetailRepository extends JpaRepository<Cartdetail, CartdetailId>  {
    List<Cartdetail> findAllByIdCartId(Long cartId);

    void deleteAllById_CartId(Long cartId);
}