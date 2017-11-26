package com.hilum.sso.oauth2;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OTPAuthorizationToken extends UsernamePasswordAuthenticationToken {
    private String otp;
    private String mobileno;

    public OTPAuthorizationToken(String mobile, String otp ) {
        super( mobile, otp );
        this.otp = otp;
    }

    public String getOTP() {
        return otp;
    }

    public String getMobileno() {
        return mobileno;
    }
}
