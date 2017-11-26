package com.hilum.sso.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class OTPSendRequest {
    @NotBlank
    @Length(min = 11, max = 11, message = "电话号码长度必须11位")
    private String mobileno;

    @NotBlank
    private String clientId;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno == null ? null : mobileno.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }
}
