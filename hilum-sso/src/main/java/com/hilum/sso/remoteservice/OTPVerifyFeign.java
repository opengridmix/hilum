package com.hilum.sso.remoteservice;

import com.hilum.sso.dto.OTPSendRequest;
import com.hilum.sso.dto.OTPSendResult;
import com.hilum.sso.dto.OTPVerifyRequest;
import com.hilum.sso.dto.OTPVerifyResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = ("${hilum.otpurl}"), name = "hilum-otp")
public interface OTPVerifyFeign {
    @RequestMapping(value = "/oauth/verify", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    OTPVerifyResult verifyOTP(@RequestBody OTPVerifyRequest otpVerifyRequest);

    @RequestMapping(value = "/oauth/send", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    OTPSendResult sendOTP(@RequestBody OTPSendRequest otpSendRequest);
}
