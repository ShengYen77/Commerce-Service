package com.gtelant.commerce.service.services;

import com.gtelant.commerce.service.dtos.UserRequest;
import com.gtelant.commerce.service.dtos.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse createUser(UserRequest request);
}
