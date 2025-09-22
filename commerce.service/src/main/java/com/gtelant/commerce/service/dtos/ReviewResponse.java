package com.gtelant.commerce.service.dtos;

import com.gtelant.commerce.service.models.ReviewStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponse {
    private Integer reviewId;
    private Integer productId;
    private String productName;   // 額外帶出
    private Integer userId;
    private String userName;      // 額外帶出
    private int rating;
    private String comment;
    private ReviewStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
