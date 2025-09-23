package com.gtelant.commerce.service.controllers;

import com.gtelant.commerce.service.dtos.ProductCategoryRequest;
import com.gtelant.commerce.service.dtos.ProductCategoryResponse;
import com.gtelant.commerce.service.services.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    // 建立產品類別
    @PostMapping
    public ResponseEntity<ProductCategoryResponse> createCategory(@RequestBody ProductCategoryRequest request) {
        ProductCategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }

    // 查詢所有產品類別(過濾已刪除)
    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getAllCategories() {
        List<ProductCategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // 查詢單一產品類別(過濾已刪除)
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getCategoryById(@PathVariable Integer id) {
        try {
            ProductCategoryResponse response = categoryService.getCategoryById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新產品類別(過濾已刪除)
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> updateCategory(
            @PathVariable Integer id,
            @RequestBody ProductCategoryRequest request) {
        try {
            ProductCategoryResponse response = categoryService.updateCategory(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除產品類別(軟刪除)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
