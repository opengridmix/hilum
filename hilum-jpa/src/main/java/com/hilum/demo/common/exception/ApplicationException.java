package com.hilum.demo.common.exception;

public class ApplicationException extends ApiException {
    private String defaultErrorCode = "200000";

    protected String getDefaultErrorCode() {
        return defaultErrorCode;
    }

    public ApplicationException(Throwable ex) {
        super(ex);
    }
}
