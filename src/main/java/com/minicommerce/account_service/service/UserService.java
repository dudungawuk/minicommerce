package com.minicommerce.account_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.minicommerce.account_service.dto.LoginRequest;
import com.minicommerce.account_service.dto.RegisterRequest;
import com.minicommerce.account_service.entity.OtpRegistration;
import com.minicommerce.account_service.entity.User;
import com.minicommerce.account_service.repository.OtpRegistrationRepository;
import com.minicommerce.account_service.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private OtpRegistrationRepository otpRegistrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public OtpRegistration register(RegisterRequest registerRequest) {
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
        String generateOTP = "123456";

        OtpRegistration tempData = new OtpRegistration(
            registerRequest.getEmail(), 
            registerRequest.getUsername(), 
            passwordEncoder.encode(registerRequest.getPassword()), 
            registerRequest.getRole(), 
            generateOTP);

        return otpRegistrationRepository.save(tempData);
    }

    public User verifyOTPAndSaveToPostgres(String email,String userInputOTP){
        OtpRegistration tempUser = otpRegistrationRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("OTP expired or session not found"));
        if (userInputOTP.equals(tempUser.getOtp())) {
            User user = new User(tempUser.getUsername(), tempUser.getEmail(), passwordEncoder.encode(tempUser.getPassword()), tempUser.getRole());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid OTP");
        }
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

        return jwtService.generateToken(user.getUsername(),user.getRole());
    }
}
