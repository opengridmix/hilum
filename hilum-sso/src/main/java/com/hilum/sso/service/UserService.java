package com.hilum.sso.service;

import com.hilum.sso.common.utils.SnowFlakeIdGenerator;
import com.hilum.sso.mapper.ClientMapper;
import com.hilum.sso.mapper.UserMapper;
import com.hilum.sso.model.Client;
import com.hilum.sso.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    public User register(String clientId, User userToAdd) {
        Client client = clientMapper.selectByClientId(clientId);
        if(client == null) {
            return null;
        }
        String realm = client.getRealm();
        final String username = userToAdd.getUsername();
        if(userMapper.selectByUsername(realm, username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setId(snowFlakeIdGenerator.nextId());
        userToAdd.setRealm(realm);
        userToAdd.setEnabled(true);
        int update = userMapper.insert(userToAdd);
        return  update == 1 ? userToAdd : null;
    }

    public User findUserByMobileNo(final String clientId, final String mobile) {
        Client client = clientMapper.selectByClientId(clientId);
        if(client == null) {
            return null;
        }
        String realm = client.getRealm();
        return userMapper.selectByMobileno(realm, mobile);
    }
}
