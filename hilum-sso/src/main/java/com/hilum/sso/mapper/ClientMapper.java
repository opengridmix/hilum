package com.hilum.sso.mapper;

import com.hilum.sso.model.Client;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.base.BaseSelectMapper;

public interface ClientMapper extends BaseSelectMapper<Client> {
    @Select("select * from client where client_id=#{clientId}")
    Client selectByClientId(String clientId);
}
