package com.minicommerce.account_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicommerce.account_service.dto.LoginRequest;
import com.minicommerce.account_service.dto.OTPRegisterRequest;
import com.minicommerce.account_service.dto.RegisterRequest;
import com.minicommerce.account_service.service.UserService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPRegisterRequest otpRegisterRequest) {
        userService.verifyOTPAndSaveToPostgres(otpRegisterRequest.getEmail(), otpRegisterRequest.getOtp());
        return ResponseEntity.ok("OTP verified successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
