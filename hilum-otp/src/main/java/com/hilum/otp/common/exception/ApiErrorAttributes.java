package com.hilum.otp.common.exception;

import com.hilum.otp.common.utils.ObjectUtils;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@Configuration
public class ApiErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errors = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Object msg = errors.get("message");
        if(!ObjectUtils.isBlank(msg) && msg instanceof String) {
            String message = (String)msg;
            if(message.startsWith("Request processing failed;")) {
                int colonIndex = message.indexOf(":");
                message = message.substring(colonIndex + 2);
                errors.put("message", message);
            }
        }

        Object exp = errors.get("exception");
        if(!ObjectUtils.isBlank(exp) && exp instanceof String) {
            String exception = (String)exp;
            int commaIndex = exception.lastIndexOf(".");
            exception = exception.substring(commaIndex + 1);
            errors.put("exception", exception);
        }

        Throwable throwable = getError(requestAttributes);
        if(throwable instanceof ApiException) {
            ApiException apiException = (ApiException)throwable;
            errors.put("code", apiException.getErrorCode());
        }

        return errors;
    }

    @Override
    public Throwable getError(RequestAttributes requestAttributes) {
        return super.getError(requestAttributes);
    }
}
