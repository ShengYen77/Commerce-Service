package com.gtelant.commerce.service.services;

import com.gtelant.commerce.service.dtos.ProductCategoryRequest;
import com.gtelant.commerce.service.dtos.ProductCategoryResponse;
import com.gtelant.commerce.service.mappers.ProductCategoryMapper;
import com.gtelant.commerce.service.models.ProductCategory;
import com.gtelant.commerce.service.repositories.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;
    private final ProductCategoryMapper categoryMapper;

    public ProductCategoryService(ProductCategoryRepository categoryRepository,
                                  ProductCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public ProductCategoryResponse createCategory(ProductCategoryRequest request) {
        ProductCategory category = categoryMapper.toEntity(request);
        category.setCreatedAt(LocalDateTime.now());
        ProductCategory saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public List<ProductCategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .filter(c -> c.getDeletedAt() == null)
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductCategoryResponse getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public ProductCategoryResponse updateCategory(Integer id, ProductCategoryRequest request) {
        ProductCategory category = categoryRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedAt(LocalDateTime.now());

        ProductCategory updated = categoryRepository.save(category);
        return categoryMapper.toResponse(updated);
    }

    public void deleteCategory(Integer id) {
        ProductCategory category = categoryRepository.findById(id)
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
}
