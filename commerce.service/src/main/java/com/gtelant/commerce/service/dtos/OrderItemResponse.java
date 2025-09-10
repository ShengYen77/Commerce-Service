package com.gtelant.commerce.service.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private Integer orderItemId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;   // 單價
    private BigDecimal totalPrice;  // 總價 (unitPrice * quantity)
}
