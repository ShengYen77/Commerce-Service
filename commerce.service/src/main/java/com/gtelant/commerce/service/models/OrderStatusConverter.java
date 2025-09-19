package com.gtelant.commerce.service.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    // Java Enum → DB
    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        return status == null ? null : status.name().toLowerCase();  // 存小寫
    }

    // DB → Java Enum
    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : OrderStatus.valueOf(dbData.toUpperCase()); // 轉成大寫 Enum
    }
}
