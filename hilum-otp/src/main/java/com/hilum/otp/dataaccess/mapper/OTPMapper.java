package com.hilum.otp.dataaccess.mapper;

import com.hilum.otp.dataaccess.model.OTP;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.BaseMapper;

public interface OTPMapper extends BaseMapper<OTP> {
    @Select("select * from otp where client_id = #{clientId} and mobile_no = #{mobile} and Date(send_time) = Date(now()) order by send_time desc limit 1")
    @Results({
            @Result(id=true, column="id", property="id"),
            @Result(column = "send_status", property = "sendStatus", typeHandler = org.apache.ibatis.type.EnumOrdinalTypeHandler.class),
            @Result(column = "last_verify_status", property = "lastVerifyStatus", typeHandler = org.apache.ibatis.type.EnumOrdinalTypeHandler.class)
    })
    OTP selectLastOTP(@Param("clientId") String clientId, @Param("mobile") String mobile);

    OTP selectLastUnverifiedOTP(String clientId, String mobile);

    @Insert("insert into otp(id, client_id, mobile_no, otp, send_time, send_status, last_verify_time, last_verify_status, failed_verify_times) values " +
            "(#{id}, #{clientId}, #{mobileNo}, #{otp}, #{sendTime}, #{sendStatus, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler}," +
            "#{lastVerifyTime}, #{lastVerifyStatus, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler}, #{failedVerifyTimes})"
    )
    int insert(OTP otp);

    @Update("update otp set " +
            "client_id = #{clientId}," +
            "mobile_no = #{mobileNo}," +
            "otp = #{otp}," +
            "send_time = #{sendTime}," +
            "send_status = #{sendStatus, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler}," +
            "last_verify_time = #{lastVerifyTime}," +
            "last_verify_status = #{lastVerifyStatus, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler}," +
            "failed_verify_times = #{failedVerifyTimes} " +
            "where id=#{id}"
    )
    int update(OTP otp);
}
