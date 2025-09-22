package com.gtelant.commerce.service.services.impl;

import com.gtelant.commerce.service.dtos.ReviewRequest;
import com.gtelant.commerce.service.dtos.ReviewResponse;
import com.gtelant.commerce.service.mappers.ReviewMapper;
import com.gtelant.commerce.service.models.*;
import com.gtelant.commerce.service.repositories.ProductRepository;
import com.gtelant.commerce.service.repositories.ReviewRepository;
import com.gtelant.commerce.service.repositories.UserRepository;
import com.gtelant.commerce.service.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Review review = new Review();
        review.setProduct(product);
        review.setUser(user);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setStatus(ReviewStatus.VISIBLE);

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse updateReview(Integer reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        review.setStatus(ReviewStatus.HIDDEN);
        reviewRepository.save(review);
    }

    @Override
    public ReviewResponse changeStatus(Integer reviewId, String status) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        ReviewStatus newStatus = ReviewStatus.valueOf(status.toUpperCase());
        review.setStatus(newStatus);
        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse getReviewById(Integer reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));
    }

    @Override
    public List<ReviewResponse> getReviewsByProduct(Integer productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewsByUser(Integer userId) {
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReviewResponse> getPagedReviewsByProduct(Integer productId, ReviewStatus status, Pageable pageable) {
        Page<Review> page = (status != null)
                ? reviewRepository.findByProductIdAndStatus(productId, status, pageable)
                : reviewRepository.findByProductId(productId, pageable);
        return page.map(reviewMapper::toResponse);
    }

    @Override
    public Page<ReviewResponse> getPagedReviewsByUser(Integer userId, ReviewStatus status, Pageable pageable) {
        Page<Review> page = (status != null)
                ? reviewRepository.findByUserIdAndStatus(userId, status, pageable)
                : reviewRepository.findByUserId(userId, pageable);
        return page.map(reviewMapper::toResponse);
    }
}
