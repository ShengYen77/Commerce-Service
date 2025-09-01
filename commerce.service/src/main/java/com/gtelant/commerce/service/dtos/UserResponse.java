package com.gtelant.commerce.service.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String role;
    private Boolean hasNewsletter;
    private LocalDateTime lastSeenAt;
    private LocalDateTime createdAt;
    private List<UserSegmentResponse> segments;
}