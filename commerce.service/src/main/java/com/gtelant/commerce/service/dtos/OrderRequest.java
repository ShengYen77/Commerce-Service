package com.gtelant.commerce.service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private Integer userId;               // 下單用戶
    private List<OrderItemRequest> items; // 訂單商品清單
    private BigDecimal totalAmount;       // 訂單總金額
}
