package com.gtelant.commerce.service.dtos;

import com.gtelant.commerce.service.models.ReviewStatus;
import lombok.Data;

@Data
public class ReviewRequest {
    private Integer productId;
    private Integer userId;
    private int rating;
    private String comment;
}
