package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    // 根據訂單查詢所有項目
    List<OrderItem> findByOrderId(Integer orderId);
}
