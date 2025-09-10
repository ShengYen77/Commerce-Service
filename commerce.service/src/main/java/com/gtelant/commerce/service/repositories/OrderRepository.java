package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.Order;
import com.gtelant.commerce.service.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // 根據用戶查詢訂單
    List<Order> findByUserId(Integer userId);

    // 根據狀態查詢訂單
    List<Order> findByStatus(OrderStatus status);

    // 根據用戶 + 狀態查詢
    List<Order> findByUserIdAndStatus(Integer userId, OrderStatus status);
}
