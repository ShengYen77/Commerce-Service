package com.gtelant.commerce.service.services.impl;

import com.gtelant.commerce.service.dtos.UserRequest;
import com.gtelant.commerce.service.dtos.UserResponse;
import com.gtelant.commerce.service.mappers.UserMapper;
import com.gtelant.commerce.service.models.User;
import com.gtelant.commerce.service.repositories.UserRepository;
import com.gtelant.commerce.service.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email 已存在");
        }

        User user = UserMapper.toEntity(request);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
}
