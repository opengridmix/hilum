package com.hilum.otp.service;

import com.hilum.otp.common.exception.SMSException;
import com.hilum.otp.common.utils.DateTimeUtils;
import com.hilum.otp.common.utils.SnowFlakeIdGenerator;
import com.hilum.otp.dataaccess.mapper.OTPMapper;
import com.hilum.otp.dataaccess.mapper.OTPTypeMapper;
import com.hilum.otp.dataaccess.mapper.OTPUsageMapper;
import com.hilum.otp.dataaccess.model.OTP;
import com.hilum.otp.dataaccess.model.OTPType;
import com.hilum.otp.dataaccess.model.OTPUsage;
import com.hilum.otp.otp.smssl.SmsSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class OTPService {

    @Autowired
    private OTPTypeMapper otpTypeMapper;

    @Autowired
    private OTPUsageMapper otpUsageMapper;

    @Autowired
    private SmsSendUtils smsSendUtils;

    @Autowired
    private OTPMapper otpMapper;

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    public long sendOTP(final String clientId, final String mobile) {
        long otpid = 0;
        OTPType otpType = otpTypeMapper.selectByClientId(clientId);
        if(otpType == null) {
            throw new SMSException("没有短信配置信息");
        }
        //仅查询当天的记录，一天一条使用记录
        OTPUsage usage = otpUsageMapper.selectByClientAndMobileno(clientId, mobile);
        OTP password = otpMapper.selectLastOTP(clientId, mobile);
        boolean canSend = true;
        String errorMessage = "OK";
        //检查是否刚发不久
        if(password != null) {
            Duration diff = DateTimeUtils.timeDiff(password.getSendTime());
            if(diff.getSeconds() < otpType.getSendDuration()) {
                errorMessage = "距离上次发送还不到" + otpType.getSendDuration() + "秒";
                canSend = false;
            }
        }

        boolean isSameHour = false;

        if(canSend && usage != null) {
            LocalDateTime sendTime = DateTimeUtils.toLocalDateTime(password.getSendTime());
            isSameHour = DateTimeUtils.isSameHour(sendTime, LocalDateTime.now());
            //检查是否超限
             if(isSameHour && usage.getHourCount() > otpType.getHourCount()) {
                errorMessage = "每小时最多发送" + otpType.getDayCount() + "条短信";
                canSend = false;
            }else if (usage.getDayCount() > otpType.getDayCount() ) {
                errorMessage = "每天最多发送" + otpType.getDayCount() + "条短信";
                canSend = false;
            }
        }

        if(canSend) {
            password = smsSendUtils.sendOTP(mobile, otpType, null);
            password.setId(snowFlakeIdGenerator.nextId());
            password.setClientId(clientId);
            password.setLastVerifyStatus(OTP.VerifyStatus.NONE);
            int inserted = otpMapper.insert(password);
            if(inserted == 1) {
                otpid = password.getId();
            }
            //不管发送成功失败，都需要记录，防止有人恶意刷
            if (usage != null) {
                usage.incrDayCount();
                if (isSameHour) {
                    usage.incrHourCount();
                }
                usage.setLastSentTime(new Date());
                otpUsageMapper.updateByPrimaryKey(usage);
            } else {
                OTPUsage smsUsage = new OTPUsage();
                smsUsage.setId(snowFlakeIdGenerator.nextId());
                smsUsage.setClientId(clientId);
                smsUsage.setMobileNo(mobile);
                smsUsage.setDayCount(1);
                smsUsage.setHourCount(1);
                smsUsage.setLastSentTime(new Timestamp(System.currentTimeMillis()));
                otpUsageMapper.insert(smsUsage);
            }
        } else {
            throw new SMSException(errorMessage);
        }
        return otpid;
    }

    public String verifyOTP(final String clientId, final String mobile, final String otp) {
        OTPType otpType = otpTypeMapper.selectByClientId(clientId);
        OTP password = otpMapper.selectLastOTP(clientId, mobile);

        ;
        if(password != null) {
            password.setLastVerifyTime(new Date());
            OTP.VerifyStatus lastVerifyStatus  = password.getLastVerifyStatus();
            OTP.VerifyStatus verifyStatus = OTP.VerifyStatus.NONE;
            boolean optSame = password.getOtp().equals(otp);
            //当前时间大于时间
            Duration diff = DateTimeUtils.timeDiff(password.getSendTime());
            boolean expired = diff.getSeconds() > otpType.getExpireDuration();
            boolean toomany = password.getFailedVerifyTimes() >= otpType.getMaxVerifyTimes();
            switch (lastVerifyStatus) {
                case EXPIRED:
                    verifyStatus = OTP.VerifyStatus.EXPIRED;
                    break;
                case TOO_MANY_TIMES:
                    verifyStatus = OTP.VerifyStatus.TOO_MANY_TIMES;
                    break;
                case SUCCESS:
                    if(expired) {
                        verifyStatus = OTP.VerifyStatus.EXPIRED;
                    } else if(optSame) {
                        verifyStatus = OTP.VerifyStatus.TOO_MANY_TIMES;
                    }
                    break;
                case NONE:
                    if(expired) {
                        verifyStatus = OTP.VerifyStatus.EXPIRED;
                    } else {
                        if (optSame) {
                            verifyStatus = OTP.VerifyStatus.SUCCESS;
                        } else {
                            verifyStatus = OTP.VerifyStatus.FAILURE;
                        }
                    }
                    break;
                case FAILURE:
                    if(expired) {
                       verifyStatus = OTP.VerifyStatus.EXPIRED;
                    } else if(toomany) {
                        verifyStatus = OTP.VerifyStatus.TOO_MANY_TIMES;
                    } else if(optSame) {
                        verifyStatus = OTP.VerifyStatus.SUCCESS;
                    } else {
                        verifyStatus = OTP.VerifyStatus.FAILURE;
                    }
                    break;
            }
            password.setLastVerifyStatus(verifyStatus);
            if(!verifyStatus.equals(OTP.VerifyStatus.SUCCESS)) {
                password.setFailedVerifyTimes(password.getFailedVerifyTimes() + 1);
            }
            boolean update = password.getFailedVerifyTimes() <= 10;
            //防止恶意校验
            if(update) {
                otpMapper.update(password);
            }
        }
        String errorMessage;
        if(password == null) {
            errorMessage = "请先发送短信";
        } else {
            OTP.VerifyStatus status = password.getLastVerifyStatus();
            switch (status) {
                case TOO_MANY_TIMES:
                    errorMessage = "校验次数过多";
                    break;
                case FAILURE:
                    errorMessage = "验证码不匹配";
                    break;
                case EXPIRED:
                    errorMessage = "验证码已失效";
                    break;
                default:
                    errorMessage = "";
                    break;
            }
        }
        return errorMessage;
    }

    public void checkIPStrict(String ip) {
        //TODO: 增加每一ip限制
    }
}
