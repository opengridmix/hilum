package com.hilum.otp.otp.smssl;

import com.hilum.otp.dataaccess.model.OTPType;
import com.hilum.otp.otp.OTPSendStrategy;
import com.hilum.otp.otp.SMSParam;
import com.hilum.otp.otp.SendResult;
import com.hilum.otp.otp.VerifyResult;

public class SLSendStrategy implements OTPSendStrategy {
    @Override
    public SendResult sendOTP(final SMSParam param, final OTPType otpType, final String otp) {
        return null;
    }

    @Override
    public VerifyResult verifyOTP(final OTPType otpType, final String mobileno, final String otp) {
        return null;
    }
}
