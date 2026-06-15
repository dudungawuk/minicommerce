package com.minicommerce.account_service.dto;

public class OTPRegisterRequest {
    private String email;
    private String otp;

    public OTPRegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
