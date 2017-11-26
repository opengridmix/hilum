package com.hilum.otp.otp.smssl;

import com.hilum.otp.common.Constants;
import com.hilum.otp.common.utils.ObjectUtils;
import com.hilum.otp.dataaccess.model.OTP;
import com.hilum.otp.dataaccess.model.OTPType;
import com.hilum.otp.otp.OtpGenerator;
import com.hilum.otp.otp.SMSParam;
import com.hilum.otp.otp.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SmsSendUtils {

    @Autowired
    private SmsFeign smsFeign;


    protected SendResult realSendOTP(String telephone, OTPType otpType, Map params) {
        SMSParam sendSMSParam = new SMSParam();
        sendSMSParam.setMobileNo(telephone);
        sendSMSParam.setTemplateNo(otpType.getTemplateNo());
        sendSMSParam.setRequestId(String.valueOf(UUID.randomUUID()));
        sendSMSParam.setRequestTime(String.valueOf(System.currentTimeMillis()));
        sendSMSParam.setSystemName(otpType.getAppName());
        sendSMSParam.setSceneType(otpType.getScene());
        String otp = getOTP(otpType);
        params.put(Constants.OTP, otp);
        sendSMSParam.setParams(params);
        SendResult result;
        if(!otpType.isExternal()) {
            result = sendOTPInternal(sendSMSParam, otp);
        } else {
            result = sendOTPExternal(sendSMSParam, otp, otpType);
        }
        result.setOtp(otp);
        return result;
    }

    protected SendResult sendOTPInternal(SMSParam smsParam, String otp) {
        //调用feign 短信服务有异常，需要捕获异常
        SendResult result = null;
        result = new SendResult();
        result.setOtp(otp);
        result.setMessageUUID(UUID.randomUUID().toString());

//        try {
//            result = smsFeign.sendSMS(smsParam);
//        } catch (Exception ex) {
//            //FIXME:use exception instead
//            //throw new SMSException(ex);
//
//        }
        return result;
    }

    protected SendResult sendOTPExternal(SMSParam smsParam, String otp, OTPType otpType) {
        SendResult result = null;
        if(ObjectUtils.isBlank(otpType.getClassName())) {
            throw new IllegalArgumentException("外部发送失败");
        }

        //Class<? extends SmsSender> clazz = Class.forName(otpType.getClassName());
        //clazz.realSendOTP(smsParam, otp, otpType);

        return result;
    }



    protected String getOTP(OTPType otpType) {
        OtpGenerator otpGenerator = new OtpGenerator(otpType.getOtpLength());
        return otpGenerator.generateToken();
    }

    protected OTP buildOTP(String telephone, OTPType otpType, SendResult result) {
        OTP oneTimePassword = new OTP();
        if(result != null && !ObjectUtils.isBlank(result.getMessageUUID())) {
            oneTimePassword.setSendStatus(OTP.SendStatus.SUCCESS);
        } else {
            oneTimePassword.setSendStatus(OTP.SendStatus.FAILURE);
        }
        oneTimePassword.setSendTime(new Date());
        oneTimePassword.setFailedVerifyTimes(0);
        oneTimePassword.setMobileNo(telephone);
        oneTimePassword.setLastVerifyTime(null);
        oneTimePassword.setOtp(result.getOtp());
        return oneTimePassword;
    }

    public OTP sendOTP(String telephone, OTPType otpType, Map params) {
        if(ObjectUtils.isBlank(params)) {
            params = new HashMap<String, Object>();
        }
        SendResult result = realSendOTP(telephone, otpType, params);
        OTP oneTimePassword = buildOTP(telephone, otpType, result);
        return oneTimePassword;
    }
}
