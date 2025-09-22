package com.gtelant.commerce.service.repositories;

import com.gtelant.commerce.service.models.Review;
import com.gtelant.commerce.service.models.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductId(Integer productId);
    List<Review> findByUserId(Integer userId);

    Page<Review> findByProductId(Integer productId, Pageable pageable);
    Page<Review> findByUserId(Integer userId, Pageable pageable);

    Page<Review> findByProductIdAndStatus(Integer productId, ReviewStatus status, Pageable pageable);
    Page<Review> findByUserIdAndStatus(Integer userId, ReviewStatus status, Pageable pageable);
}

