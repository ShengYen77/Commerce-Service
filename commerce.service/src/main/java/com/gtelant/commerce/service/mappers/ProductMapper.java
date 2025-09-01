package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.ProductRequest;
import com.gtelant.commerce.service.dtos.ProductResponse;
import com.gtelant.commerce.service.models.Product;
import com.gtelant.commerce.service.models.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // DTO → Entity
    public Product toEntity(ProductRequest request, ProductCategory category) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);
        return product;
    }

    // Entity → DTO
    public ProductResponse toResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());
        return dto;
    }
}
