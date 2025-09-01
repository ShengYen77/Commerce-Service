package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    // 可依名稱查詢
    ProductCategory findByName(String name);
}
