package com.repository;

import com.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    @Procedure
    public Object[] calculateRevenueByDate(String datePicker);

    @Procedure
    public Object[] calculateRevenueByCategory(Long catId);
}