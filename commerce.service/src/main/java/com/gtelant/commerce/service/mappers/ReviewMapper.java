package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.ReviewRequest;
import com.gtelant.commerce.service.dtos.ReviewResponse;
import com.gtelant.commerce.service.models.Product;
import com.gtelant.commerce.service.models.Review;
import com.gtelant.commerce.service.models.ReviewStatus;
import com.gtelant.commerce.service.models.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    // DTO → Entity (instance method)
    public Review toEntity(ReviewRequest request, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setStatus(ReviewStatus.VISIBLE);
        return review;
    }

    // Entity → DTO (instance method)
    public ReviewResponse toResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setReviewId(review.getId());
        response.setProductId(review.getProduct() != null ? review.getProduct().getId() : null);
        response.setProductName(review.getProduct() != null ? review.getProduct().getName() : "N/A");
        response.setUserId(review.getUser() != null ? review.getUser().getId() : null);
        response.setUserName(review.getUser() != null ?
                (review.getUser().getFirstName() + " " + review.getUser().getLastName()) : "Anonymous");
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setStatus(review.getStatus());
        response.setCreatedAt(review.getCreatedAt());
        response.setUpdatedAt(review.getUpdatedAt());
        return response;
    }
}
