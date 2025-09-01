package com.gtelant.commerce.service.dtos;

import lombok.Data;

@Data
public class ProductCategoryResponse {
    private Integer categoryId;
    private String name;
    private String description;
}
