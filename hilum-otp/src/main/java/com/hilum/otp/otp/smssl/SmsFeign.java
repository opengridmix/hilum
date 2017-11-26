package com.hilum.otp.otp.smssl;

import com.hilum.otp.otp.SMSParam;
import com.hilum.otp.otp.SendResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = ("${hilum.smsurl}"), name = "hilum-app")
public interface SmsFeign {
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    SendResult sendSMS(@RequestBody SMSParam sendSMSParam);
}
