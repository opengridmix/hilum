package com.hilum.sso.oauth2;

import com.hilum.sso.common.utils.ObjectUtils;
import com.hilum.sso.model.User;
import com.hilum.sso.service.ClientService;
import com.hilum.sso.service.OTPService;
import com.hilum.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OTPAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private ClientService clientService;

    @Override
    public Authentication authenticate(Authentication authentication ) throws AuthenticationException {
        if(! (authentication.getDetails() instanceof Map)) {
            return null;
        }
        //OTPAuthorizationToken oauth2 = (OTPAuthorizationToken)authentication;
        Map<String, String> details = (Map<String, String>)authentication.getDetails();
        String mobile = details.get("mobileno");
        String otp = details.get("otp");
        String clientId = details.get("clientId");
        String create = details.get("create");

        if (ObjectUtils.isBlank(mobile) || ObjectUtils.isBlank(otp)) {
            return null;
        }

        boolean autoCreate = Boolean.parseBoolean(create);
        User user = userService.findUserByMobileNo(clientId, mobile);
        if(user == null) {
            if(autoCreate) {
                user = new User();
                user.setUserName(mobile);
                user.setMobileNo(mobile);
                user.setPassword("888888");
                user = userService.register(clientId, user);
                if(user == null) {
                    throw new UsernameNotFoundException("不能创建用户");
                }
            } else {
                throw new UsernameNotFoundException("找不到用户");
            }
        }

        if(!user.isEnabled()) {
            throw new LockedException("用户被锁定");
        }

//        Client client = clientService.getServiceByClientId(clientId);
//
//        if(ObjectUtils.isBlank(client)) {
//            throw new UsernameNotFoundException("找不到用户");
//        }

        otpService.verifyOTP(mobile, otp, clientId);

        String principal = (String)authentication.getPrincipal();
        if(ObjectUtils.isBlank(principal)) {
            principal = user.getUsername();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user,
                user.getAuthorities());
        return token;
    }

    @Override
    public boolean supports( Class<?> authentication ) {
        if ( authentication.isAssignableFrom( OTPAuthorizationToken.class ) ) {
            return true;
        }
        return false;
    }
}