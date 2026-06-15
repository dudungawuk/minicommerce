package com.minicommerce.account_service.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("OtpRegistration")
public class OtpRegistration implements Serializable{

    @Id
    private String email;
    private String username;
    private String password;
    private String role;
    private String otp;
    
    @TimeToLive
    private Long ttl = 300L;

    public OtpRegistration(String email, String username, String password, String role, String otp) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.otp = otp;
    }

    public OtpRegistration() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
