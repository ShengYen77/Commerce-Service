package com.gtelant.commerce.service.controllers;

import com.gtelant.commerce.service.dtos.LoginRequest;
import com.gtelant.commerce.service.dtos.LoginResponse;
import com.gtelant.commerce.service.models.User;
import com.gtelant.commerce.service.repositories.UserRepository;
import com.gtelant.commerce.service.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // 根據 email 找 User
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // **不加密比對**
        if (!user.getPassword().equals(request.getPassword())) {
            // 密碼錯誤
            return ResponseEntity.status(403).build(); // 或 401
        }

        // 產生 JWT
        String token = jwtUtil.generateToken(user.getEmail());

        // 回傳資料
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return ResponseEntity.ok(response);
    }
}
