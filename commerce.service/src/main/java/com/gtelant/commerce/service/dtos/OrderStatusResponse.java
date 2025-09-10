package com.gtelant.commerce.service.dtos;

import lombok.Data;

@Data
public class OrderStatusResponse {
    private String code;   // 例如 PENDING
    private String label;  // 顯示用：待處理、已付款、已出貨...
}
