package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // 根據類別查詢
    List<Product> findByCategoryId(Integer categoryId);

    // 關鍵字搜尋 (產品名稱模糊查詢)
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
