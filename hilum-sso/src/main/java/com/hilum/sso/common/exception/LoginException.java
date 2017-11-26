package com.hilum.sso.common.exception;

public class LoginException extends ApplicationException {
    private String defaultErrorCode = "200002";

    protected String getDefaultErrorCode() {
        return defaultErrorCode;
    }

    public LoginException(final Throwable ex) {
        super(ex);
    }

    public LoginException(final String message) {
        super(message);
    }
}
