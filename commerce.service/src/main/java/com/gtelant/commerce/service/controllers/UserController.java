package com.gtelant.commerce.service.controllers;

import com.gtelant.commerce.service.dtos.UserRequest;
import com.gtelant.commerce.service.dtos.UserResponse;
import com.gtelant.commerce.service.dtos.UserSegmentResponse;
import com.gtelant.commerce.service.models.User;
import com.gtelant.commerce.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return ((List<User>) userRepository.findAll())
                .stream()
                .map(this::toUserResponse) // 🔹 Entity → DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(toUserResponse(user))) // 🔹 Entity → DTO
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = toUser(userRequest);
        User saved = userRepository.save(user);
        return ResponseEntity.ok(toUserResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setBirthday(userRequest.getBirthday());
        user.setAddress(userRequest.getAddress());
        user.setCity(userRequest.getCity());
        user.setState(userRequest.getState());
        user.setZipcode(userRequest.getZipcode());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        user.setHasNewsletter(userRequest.getHasNewsletter() ? "Y" : "N");

        User updated = userRepository.save(user);
        return ResponseEntity.ok(toUserResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponse toUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setCity(user.getCity());
        dto.setState(user.getState());
        dto.setZipcode(user.getZipcode());
        dto.setRole(user.getRole());
        dto.setHasNewsletter("Y".equalsIgnoreCase(user.getHasNewsletter()));
        dto.setLastSeenAt(user.getLastSeenAt());
        dto.setCreatedAt(user.getCreatedAt());

        if (user.getUserSegments() != null) {
            dto.setSegments(
                    user.getUserSegments().stream().map(us -> {
                        UserSegmentResponse segDto = new UserSegmentResponse();
                        segDto.setId(us.getId());
                        segDto.setName(us.getSegment().getName());
                        segDto.setDescription(us.getSegment().getDescription());
                        return segDto;
                    }).collect(Collectors.toList())
            );
        }
        return dto;
    }

    private User toUser(UserRequest request) {
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
        user.setHasNewsletter(request.getHasNewsletter() ? "Y" : "N");
        return user;
    }
}
