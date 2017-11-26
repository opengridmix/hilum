package com.hilum.sso.common.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.hilum.remoteservice")
public class FeignConfig {

}
