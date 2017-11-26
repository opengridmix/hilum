package com.hilum.sso.common.exception;

public class ApiException extends RuntimeException {
    private   String defaultErrorCode = "100000";
    protected String errorCode;
    protected String message;

    protected String getDefaultErrorCode() {
        return defaultErrorCode;
    }

    public ApiException() {
        this.errorCode = defaultErrorCode;
    }

    public ApiException(final String message) {
        this.message = message;
        this.errorCode = defaultErrorCode;
    }

    public ApiException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ApiException(Throwable cause) {
        super(cause);
        this.errorCode = defaultErrorCode;
    }

    public ApiException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
