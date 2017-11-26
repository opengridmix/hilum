package com.hilum.sso.controller;

import com.hilum.sso.dto.OTPLoginRequest;
import com.hilum.sso.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;

@RestController
public class MyAuthorizeController {

    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    @Autowired
    TokenEndpoint tokenEndpoint;

    @Autowired
    OTPService otpService;

    @PostMapping("/oauth/otp/login")
    @ResponseBody
    public ResponseEntity<OAuth2AccessToken> login(Principal principal, @Validated @RequestBody OTPLoginRequest loginRequest)
            throws HttpRequestMethodNotSupportedException {
        HashMap<String, String> requestParameters = new HashMap<>();
        requestParameters.put("grant_type", "password");
        requestParameters.put("mobileno", loginRequest.getMobileno());
        requestParameters.put("otp", loginRequest.getOtp());
        requestParameters.put("create", Boolean.toString(loginRequest.isCreate()));
        requestParameters.put("clientId", principal.getName());
        return tokenEndpoint.postAccessToken(principal, requestParameters);
        //return ResponseEntity.ok(null);
    }

    @PostMapping("/oauth/otp/send")
    @ResponseBody
    public ResponseEntity<?> sendotp(Principal principal, @RequestBody OTPLoginRequest loginRequest)
            throws HttpRequestMethodNotSupportedException {
        Long id = otpService.sendOTP(loginRequest.getMobileno(), principal.getName());
        HashMap<String, Long> map = new HashMap<>();
        map.put("otpid", id);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/oauth/otp/logout")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
        }
    }
}
