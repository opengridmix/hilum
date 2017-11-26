package com.hilum.otp.controller;

import com.hilum.otp.common.exception.SMSException;
import com.hilum.otp.common.utils.IPUtils;
import com.hilum.otp.common.utils.ObjectUtils;
import com.hilum.otp.controller.dto.*;
import com.hilum.otp.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OTPController {

    @Autowired
    OTPService otpService;

    @RequestMapping(method = RequestMethod.POST, value = "/otp/send")
    @ResponseBody
    public ResponseEntity<OTPSendResult> OTPSend(@Validated(NoClient.class) @RequestBody OTPSendRequest otpSendRequest, HttpServletRequest request) {
        String clientId = otpSendRequest.getClientId();
        return send(clientId, otpSendRequest.getMobileno(), request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/otp/verify")
    @ResponseBody
    public ResponseEntity<OTPVerifyResult> OTPVerify(@Validated(NoClient.class) @RequestBody OTPVerifyRequest otpVerifyRequest, HttpServletRequest request) {
        String clientId = otpVerifyRequest.getClientId();
        return verify(clientId, otpVerifyRequest.getMobileno(), otpVerifyRequest.getOtp(), request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/send")
    @ResponseBody
    public ResponseEntity<OTPSendResult> OTPLoginSend(@Validated @RequestBody OTPSendRequest otpSendRequest, HttpServletRequest request) {
        return send(otpSendRequest.getClientId(), otpSendRequest.getMobileno(), request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/verify")
    @ResponseBody
    public ResponseEntity<OTPVerifyResult> OTPLoginVerify(@Validated @RequestBody OTPVerifyRequest otpVerifyRequest, HttpServletRequest request) {
        return verify(otpVerifyRequest.getClientId(), otpVerifyRequest.getMobileno(), otpVerifyRequest.getOtp(), request);
    }

    protected void checkIPStrict(HttpServletRequest request) {
        String ip = IPUtils.getIpAddress(request);
        otpService.checkIPStrict(ip);
    }

    protected ResponseEntity<OTPSendResult> send(String clientId, String mobileno, HttpServletRequest request) {
        checkIPStrict(request);
        long optid = otpService.sendOTP(clientId, mobileno);
        OTPSendResult result  = new OTPSendResult();
        result.setOtpid(optid);
        return ResponseEntity.ok(result);
    }

    protected ResponseEntity<OTPVerifyResult> verify(String clientId, String mobileno, String otp, HttpServletRequest request) {
        checkIPStrict(request);
        String message = otpService.verifyOTP(clientId, mobileno, otp);
        OTPVerifyResult verifyResult = new OTPVerifyResult();
        if(ObjectUtils.isBlank(message)) {
            verifyResult.setStatus(0);
            verifyResult.setMessage("验证成功");
        } else {
            verifyResult.setStatus(1);
            verifyResult.setMessage(message);
        }
        return ResponseEntity.ok(verifyResult);
    }
}