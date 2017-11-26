package com.hilum.demo.common.exception;

public class BusinessException extends ApiException {
    private String defaultErrorCode = "200000";

    protected String getDefaultErrorCode() {
        return defaultErrorCode;
    }
}
