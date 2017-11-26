package com.hilum.otp.otp;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Map;

public class SMSParam {
    @NotNull
    @Length(min = 1, max = 60)
    private String systemName;

    @NotNull
    @Length(min = 1, max = 60)
    private String requestId;

    @NotNull
    private String requestTime;

    private String sceneType;

    @NotNull
    @Pattern(regexp = "1[0-9]{10,10}")
    private String mobileNo;

    @NotNull
    @Length(min = 1, max = 60)
    private String templateNo;

    @NotNull
    private Map<String, String> params;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(final String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(final String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(final String requestTime) {
        this.requestTime = requestTime == null ? null : requestTime.trim();
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(final String sceneType) {
        this.sceneType = sceneType == null ? null : sceneType.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(final String templateNo) {
        this.templateNo = templateNo == null ? null : templateNo.trim();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(final Map<String, String> params) {
        this.params = params;
    }
}
