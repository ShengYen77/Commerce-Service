package com.gtelant.commerce.service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private Integer userId;
    private List<OrderItemRequest> items;
    private BigDecimal totalAmount;
}
