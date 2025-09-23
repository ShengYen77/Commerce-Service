package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.Order;
import com.gtelant.commerce.service.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserIdAndStatus(Integer userId, OrderStatus status);
}
