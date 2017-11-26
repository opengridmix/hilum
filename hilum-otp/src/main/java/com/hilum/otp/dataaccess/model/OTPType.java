package com.hilum.otp.dataaccess.model;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by bjy on 16/5/3.
 */
public class OTPType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String  clientId;
    private String  scene;
    private String  appName;
    private Integer maxVerifyTimes;      //最多校验次数
    private Integer expireDuration;      //多长时间内有效(单位：秒）
    private Integer sendDuration;        //间隔多久才能再次发送(单位:秒)
    private Integer otpLength;           //otp长度
    private String  templateNo;          //模板
    private Integer hourCount;           //每小时最多多少次
    private Integer dayCount;            //每天最多多少次
    private String  url;      //参数
    private String  className; //外部校验时用
    private boolean external; //是否外部校验,外部校验仅校验返回是否成功


    public Integer getMaxVerifyTimes() {
        return maxVerifyTimes;
    }

    public Integer getExpireDuration() {
        return expireDuration;
    }

    public Integer getSendDuration() {
        return sendDuration;
    }

    public Integer getOtpLength() {
        return otpLength;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public String getClientId() {
        return clientId;
    }

    public String getScene() {
        return scene;
    }

    public String getAppName() {
        return appName;
    }

    public boolean isExternal() {
        return external;
    }

    public String getClassName() {
        return className;
    }

    public String getUrl() {
        return url;
    }

    public Integer getHourCount() {
        return hourCount;
    }

    public Integer getDayCount() {
        return dayCount;
    }
}
