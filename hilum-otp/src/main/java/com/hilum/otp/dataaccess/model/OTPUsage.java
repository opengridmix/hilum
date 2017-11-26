package com.hilum.otp.dataaccess.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "otpusage")
public class OTPUsage {
    @Id
    private Long  id;
    private String  clientId;
    private String mobileNo;
    private Integer dayCount;       //每日最多发送多少次
    private Integer hourCount;      //每时最多发送多少次
    private Date lastSentTime; //最后一次发送时间

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


    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(final Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getHourCount() {
        return hourCount;
    }

    public void setHourCount(final Integer hourCount) {
        this.hourCount = hourCount;
    }

    public Date getLastSentTime() {
        return lastSentTime;
    }

    public void setLastSentTime(final Date lastSentTime) {
        this.lastSentTime = lastSentTime;
    }

    public void incrDayCount() {
        dayCount ++;
    }

    public void incrHourCount() {
        hourCount ++;
    }
}
