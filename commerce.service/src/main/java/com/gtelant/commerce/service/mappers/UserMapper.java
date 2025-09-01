package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.models.User;
import com.gtelant.commerce.service.models.UserSegment;
import com.gtelant.commerce.service.dtos.UserRequest;
import com.gtelant.commerce.service.dtos.UserResponse;
import com.gtelant.commerce.service.dtos.UserSegmentResponse;

import java.util.stream.Collectors;

public class UserMapper {

    // Request DTO -> Entity
    public static User toEntity(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setBirthday(request.getBirthday());
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setZipcode(request.getZipcode());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setHasNewsletter(
                request.getHasNewsletter() != null ? request.getHasNewsletter().toString() : "false"
        );
        return user;
    }

    // Entity -> Response DTO
    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setCity(user.getCity());
        response.setState(user.getState());
        response.setZipcode(user.getZipcode());
        response.setRole(user.getRole());
        response.setHasNewsletter("true".equals(user.getHasNewsletter()));
        response.setLastSeenAt(user.getLastSeenAt());
        response.setCreatedAt(user.getCreatedAt());

        if (user.getUserSegments() != null) {
            response.setSegments(
                    user.getUserSegments().stream()
                            .map(UserMapper::mapSegment)
                            .collect(Collectors.toList())
            );
        }
        return response;
    }

    private static UserSegmentResponse mapSegment(UserSegment userSegment) {
        UserSegmentResponse dto = new UserSegmentResponse();
        dto.setId(userSegment.getSegment().getId());
        dto.setName(userSegment.getSegment().getName());
        dto.setDescription("N/A"); // 這裡先簡單放，等 Segment Entity 有 description 後補
        return dto;
    }
}
