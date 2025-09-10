package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.OrderItemRequest;
import com.gtelant.commerce.service.dtos.OrderItemResponse;
import com.gtelant.commerce.service.models.Order;
import com.gtelant.commerce.service.models.OrderItem;
import com.gtelant.commerce.service.models.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {

    // DTO → Entity
    public OrderItem toEntity(OrderItemRequest request, Product product, Order order) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());

        // 後端統一計算單價與總價
        BigDecimal unitPrice = product.getPrice();
        item.setUnitPrice(unitPrice);
        item.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(request.getQuantity())));

        return item;
    }

    // Entity → DTO
    public OrderItemResponse toResponse(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.setOrderItemId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}
