package com.service.impl;

import com.model.*;
import com.repository.AddressRepository;
import com.repository.OrderRepository;
import com.repository.OrderdetailRepository;
import com.service.OrderService;
import com.service.dto.CartDetailsDto;
import com.service.dto.OrderInfomationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderdetailRepository orderdetailRepository;

    @Autowired
    CartServicesImpl cartServices;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order confirmOrder(Long id) {
        Order order = findById(id);
        if (order == null || order.getStatus().equalsIgnoreCase("canceled"))
            return null;
        order.setStatus("confirmed");
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order cancelOrder(Long id) {
        Order order = findById(id);
        if (order == null || order.getStatus().equalsIgnoreCase("canceled"))
            return null;
        order.setStatus("canceled");
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order saveOrder(OrderInfomationDto orderInfomationDto, Long cartId) {
        List<CartDetailsDto> lineItems = cartServices.findAllLineItems(cartId);
        _Address address;
        Order order;

        if (!lineItems.isEmpty()) {
            address = new _Address();
            address.setDetail(orderInfomationDto.getDetail());
            address.setProvinceId(orderInfomationDto.getProvince());
            address.setDistrictId(orderInfomationDto.getDistrict());
            address.setWardId(orderInfomationDto.getWard());

            order = new Order();
            order.setDeliveryFee(5);
            order.setFullname(orderInfomationDto.getFullname());
            order.setPhone(orderInfomationDto.getPhone());
            order.setTotalPrice(0);
            order.setUserId(orderInfomationDto.getUserId());
            order.setDateCreate(Calendar.getInstance().getTime());
            order.setStatus("waiting");
            address = addressRepository.save(address);

            order.setAddress(address.getId());
            Order savedOrder = orderRepository.save(order);

            AtomicReference<Integer> totalPrice = new AtomicReference<>(0);
            lineItems.forEach(item -> {
                OrderdetailId orderdetailId = new OrderdetailId();
                orderdetailId.setOrderId(savedOrder.getId());
                orderdetailId.setProductId(item.getProduct());

                Orderdetail orderdetail = new Orderdetail();
                orderdetail.setId(orderdetailId);
                orderdetail.setQuantity(item.getQuantity());
                orderdetail.setPrice(item.getProduct().getPrice());

                orderdetailRepository.save(orderdetail);
                totalPrice.updateAndGet(v -> v + orderdetail.getPrice());
            });
            savedOrder.setTotalPrice(totalPrice.get() + order.getDeliveryFee());

            if (orderRepository.save(savedOrder) == null) return null;
            cartServices.deleteAllLineItems(cartId);
            return savedOrder;
        }
        return null;
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public List<Orderdetail> getAllItems(Long orderId) {
        return orderdetailRepository.findAllOfOrder(orderId);
    }

    @Override
    public List<Order> findAll(Integer page) {
        return orderRepository.findAll(PageRequest.of(page, 30)).toList();
    }

    @Override
    @Transactional
    public BigInteger calculateRevenueByDate(String date) {
        return (BigInteger) orderRepository.calculateRevenueByDate(date)[0];
    }

    @Override
    @Transactional
    public BigInteger calculateRevenueByCategory(Long catId) {
        return (BigInteger) orderRepository.calculateRevenueByCategory(catId)[0];
    }
}
