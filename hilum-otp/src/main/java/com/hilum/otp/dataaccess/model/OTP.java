package com.hilum.otp.dataaccess.model;

import javax.persistence.Id;
import java.util.Date;

public class OTP {
    public enum VerifyStatus {
        NONE, SUCCESS,FAILURE,EXPIRED,TOO_MANY_TIMES
    }

    public enum SendStatus {
        SUCCESS, FAILURE
    }

    @Id
    private Long id;         //id
    private String clientId;   //clientid
    private String mobileNo;   //手机号
    private String otp;        //otp

    private Integer failedVerifyTimes;  //失败校验次数
    private Date sendTime;         //发送时间
    private Date lastVerifyTime;   //最后一次校验时间
    private VerifyStatus lastVerifyStatus; //最后一次校验状态
    private SendStatus sendStatus;      //发送状态

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(final String otp) {
        this.otp = otp == null ? null : otp.trim();
    }

    public Integer getFailedVerifyTimes() {
        return failedVerifyTimes;
    }

    public void setFailedVerifyTimes(final Integer failedVerifyTimes) {
        this.failedVerifyTimes = failedVerifyTimes;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(final Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getLastVerifyTime() {
        return lastVerifyTime;
    }

    public void setLastVerifyTime(final Date lastVerifyTime) {
        this.lastVerifyTime = lastVerifyTime;
    }

    public VerifyStatus getLastVerifyStatus() {
        return lastVerifyStatus;
    }

    public void setLastVerifyStatus(final VerifyStatus lastVerifyStatus) {
        this.lastVerifyStatus = lastVerifyStatus;
    }

    public SendStatus getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(final SendStatus sendStatus) {
        this.sendStatus = sendStatus;
    }
}
