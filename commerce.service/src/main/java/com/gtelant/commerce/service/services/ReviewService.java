package com.gtelant.commerce.service.services;

import com.gtelant.commerce.service.dtos.ReviewRequest;
import com.gtelant.commerce.service.dtos.ReviewResponse;
import com.gtelant.commerce.service.models.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);
    ReviewResponse updateReview(Integer reviewId, ReviewRequest request);
    void deleteReview(Integer reviewId);
    ReviewResponse changeStatus(Integer reviewId, String status);
    ReviewResponse getReviewById(Integer reviewId);
    List<ReviewResponse> getReviewsByProduct(Integer productId);
    List<ReviewResponse> getReviewsByUser(Integer userId);

    Page<ReviewResponse> getPagedReviewsByProduct(Integer productId, ReviewStatus status, Pageable pageable);
    Page<ReviewResponse> getPagedReviewsByUser(Integer userId, ReviewStatus status, Pageable pageable);
}
