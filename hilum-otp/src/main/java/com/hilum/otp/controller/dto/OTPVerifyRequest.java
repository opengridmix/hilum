package com.hilum.otp.controller.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class OTPVerifyRequest {

    @NotBlank(groups = NoClient.class)
    @Length(min = 11, max = 11, message = "电话号码长度必须11位", groups = NoClient.class)
    private String mobileno;

    @NotBlank(groups = NoClient.class)
    @Length(min = 4, max = 8, message = "校验码长度限定在4-8位", groups = NoClient.class)
    private String otp;

    @NotBlank
    private String clientId;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp == null ? null : otp.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }
}
