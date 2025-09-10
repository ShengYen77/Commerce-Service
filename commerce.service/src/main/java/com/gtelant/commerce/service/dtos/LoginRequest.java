package com.gtelant.commerce.service.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;     // 用 email 登入
    private String password;
}
