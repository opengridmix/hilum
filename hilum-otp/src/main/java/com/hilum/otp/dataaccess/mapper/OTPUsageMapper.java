package com.hilum.otp.dataaccess.mapper;

import com.hilum.otp.dataaccess.model.OTPUsage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

public interface OTPUsageMapper extends BaseMapper<OTPUsage> {
    @Select("select * from otpusage where client_id=#{clientId} and mobile_no=#{mobile} and Date(last_sent_time) = Date(now())")
    OTPUsage selectByClientAndMobileno(@Param("clientId") String clientId, @Param("mobile") String mobile);
}
