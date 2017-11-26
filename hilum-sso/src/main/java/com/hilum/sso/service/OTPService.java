package com.hilum.sso.service;

import com.hilum.sso.common.exception.LoginException;
import com.hilum.sso.dto.OTPSendRequest;
import com.hilum.sso.dto.OTPSendResult;
import com.hilum.sso.dto.OTPVerifyRequest;
import com.hilum.sso.dto.OTPVerifyResult;
import com.hilum.sso.remoteservice.OTPVerifyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
    @Autowired
    private OTPVerifyFeign otpVerifyFeign;

    public void verifyOTP(String mobile, String otp, String clientId) {
        OTPVerifyRequest otpVerifyRequest = new OTPVerifyRequest();
        otpVerifyRequest.setMobileno(mobile);
        otpVerifyRequest.setOtp(otp);
        otpVerifyRequest.setClientId(clientId);
        OTPVerifyResult verifyResult = otpVerifyFeign.verifyOTP(otpVerifyRequest);
        //return verifyResult.getStatus() == 0;
        if(verifyResult.getStatus() != 0) {
            throw new LoginException(verifyResult.getMessage());
        }
    }

    public Long sendOTP(String mobile, String clientId) {
        OTPSendRequest otpSendRequest = new OTPSendRequest();
        otpSendRequest.setMobileno(mobile);
        otpSendRequest.setClientId(clientId);
        OTPSendResult sendResult = otpVerifyFeign.sendOTP(otpSendRequest);
        return sendResult.getOtpid();
    }
}
