package com.gtelant.commerce.service.dtos;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Integer userId;
    private String email;
    private String role;
}
