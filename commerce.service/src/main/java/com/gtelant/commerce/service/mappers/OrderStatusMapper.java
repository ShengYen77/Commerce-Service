package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.OrderStatusResponse;
import com.gtelant.commerce.service.models.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusMapper {

    public OrderStatusResponse toResponse(OrderStatus status) {
        OrderStatusResponse dto = new OrderStatusResponse();
        dto.setCode(status.name());
        dto.setLabel(mapLabel(status));
        return dto;
    }

    private String mapLabel(OrderStatus status) {
        switch (status) {
            case PENDING: return "待處理";
            case PAID: return "已付款";
            case SHIPPED: return "已出貨";
            case COMPLETED: return "已完成";
            case CANCELLED: return "已取消";
            default: return status.name();
        }
    }
}
