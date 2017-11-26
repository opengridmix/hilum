package com.hilum.otp.otp;

public class SendResult {
    private String messageUUID;
    private String otp;
    private Integer intervalSeconds;

    public String getMessageUUID() {
        return messageUUID;
    }

    public void setMessageUUID(final String messageUUID) {
        this.messageUUID = messageUUID == null ? null : messageUUID.trim();
    }

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(final Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(final String otp) {
        this.otp = otp == null ? null : otp.trim();
    }
}
