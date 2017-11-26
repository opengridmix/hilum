package com.hilum.sso.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;

@Configuration
public class OAuth2WebSecurityConfig extends AuthorizationServerSecurityConfiguration {
    @Autowired
    OTPAuthenticationProvider otpAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/oauth/otp/**");
        http.authenticationProvider(otpAuthenticationProvider);
        super.configure(http);
    }

}
