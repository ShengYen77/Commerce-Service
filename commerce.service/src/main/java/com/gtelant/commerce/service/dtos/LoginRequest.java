package com.gtelant.commerce.service.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
