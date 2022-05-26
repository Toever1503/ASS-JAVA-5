package com.service;

import com.model.Cart;
import com.model.CartdetailId;
import com.model.Order;
import com.model.Orderdetail;
import com.service.dto.OrderInfomationDto;

import java.math.BigInteger;
import java.util.List;

public interface OrderService {

    public Order findById(Long id);

    public Order confirmOrder(Long id);

    public Order cancelOrder(Long id);

    public Order saveOrder(OrderInfomationDto orderInfomationDto, Long cartId);

    public List<Order> findAllByUserId(Long userId);

    public List<Orderdetail> getAllItems(Long orderId);

    public List<Order> findAll(Integer page);

    public BigInteger calculateRevenueByDate(String date);

    public BigInteger calculateRevenueByCategory(Long catId);
}
