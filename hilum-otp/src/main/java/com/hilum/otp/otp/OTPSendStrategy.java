package com.hilum.otp.otp;

import com.hilum.otp.dataaccess.model.OTPType;

public interface OTPSendStrategy {

    SendResult sendOTP(SMSParam param, OTPType otpType, String otp);

    VerifyResult verifyOTP(OTPType otpType, String mobileno, String otp);
}
