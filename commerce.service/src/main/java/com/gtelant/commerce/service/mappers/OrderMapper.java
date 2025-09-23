package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.OrderRequest;
import com.gtelant.commerce.service.dtos.OrderResponse;
import com.gtelant.commerce.service.dtos.OrderItemRequest;
import com.gtelant.commerce.service.dtos.OrderItemResponse;
import com.gtelant.commerce.service.models.Order;
import com.gtelant.commerce.service.models.OrderItem;
import com.gtelant.commerce.service.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    // DTO → Entity
    public Order toEntity(OrderRequest request, User user, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(items);
        // totalAmount 由 Service 計算
        return order;
    }

    // Entity → DTO
    public OrderResponse toResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.setOrderId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setUserName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        if (order.getOrderItems() != null) {
            dto.setItems(order.getOrderItems().stream()
                    .map(this::mapOrderItem)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private OrderItemResponse mapOrderItem(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.setOrderItemId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice()); // 直接取 Service 計算好的值
        return dto;
    }
}
