package com.repository;

import com.model.Orderdetail;
import com.model.OrderdetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderdetailRepository extends JpaRepository<Orderdetail, OrderdetailId> {
    @Query("select o from Orderdetail o where o.id.orderId=?1")
    public List<Orderdetail> findAllOfOrder(Long id);
}