package com.hilum.otp.common.exception;

public class SMSException extends ApplicationException {
    private String defaultErrorCode = "201000";

    protected String getDefaultErrorCode() {
        return defaultErrorCode;
    }

    public SMSException(Throwable ex) {
        super(ex);
    }

    public SMSException(String message) {
        super(message);
    }
}
