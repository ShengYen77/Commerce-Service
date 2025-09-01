package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.ProductCategoryRequest;
import com.gtelant.commerce.service.dtos.ProductCategoryResponse;
import com.gtelant.commerce.service.models.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

    // DTO → Entity
    public ProductCategory toEntity(ProductCategoryRequest request) {
        ProductCategory category = new ProductCategory();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    // Entity → DTO
    public ProductCategoryResponse toResponse(ProductCategory category) {
        ProductCategoryResponse dto = new ProductCategoryResponse();
        dto.setCategoryId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
