package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
