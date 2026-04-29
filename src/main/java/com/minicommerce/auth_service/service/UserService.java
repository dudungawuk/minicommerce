package com.minicommerce.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minicommerce.auth_service.dto.LoginRequest;
import com.minicommerce.auth_service.dto.RegisterRequest;
import com.minicommerce.auth_service.entity.User;
import com.minicommerce.auth_service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User register(RegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getUsername().isBlank()) {
            throw new RuntimeException("Username is required");
        }
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getRole()
        );

        return userRepository.save(user);
    }

    public String login(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank()) {
            throw new RuntimeException("Username is required");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(user.getUsername());
    }
}
