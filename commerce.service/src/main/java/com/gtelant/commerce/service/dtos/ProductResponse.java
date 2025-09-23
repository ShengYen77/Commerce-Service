package com.gtelant.commerce.service.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Integer productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer categoryId;
    private String categoryName;
}
