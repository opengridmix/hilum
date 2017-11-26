package com.hilum.sso.mapper;

import com.hilum.sso.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where realm = #{realm} and mobile_no = #{mobile}")
    User selectByMobileno(@Param("realm") String realm, @Param("mobile") String mobile);

    @Select("select * from user where realm = #{realm} and user_name = #{username}")
    User selectByUsername(@Param("realm") String realm, @Param("username") String username);
}
