package com.hilum.sso.dto;

import org.hibernate.validator.constraints.NotBlank;

public class OTPLoginRequest {
    private boolean create = true;
    @NotBlank
    private String mobileno;
    @NotBlank
    private String otp;

    public boolean isCreate() {
        return create;
    }

    public void setCreate(final boolean create) {
        this.create = create;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(final String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(final String otp) {
        this.otp = otp == null ? null : otp.trim();
    }
}
