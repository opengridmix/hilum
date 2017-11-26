package com.hilum.otp.dataaccess.mapper;

import com.hilum.otp.dataaccess.model.OTPType;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import tk.mybatis.mapper.common.base.BaseSelectMapper;

public interface OTPTypeMapper extends BaseSelectMapper<OTPType> {
    @Select("select * from otptype where client_id=#{clientId}")
    @Cacheable(value = {"client"}, key = "#root.args[0]")
    OTPType selectByClientId(String clientId);

}
