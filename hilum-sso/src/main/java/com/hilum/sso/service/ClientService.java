package com.hilum.sso.service;

import com.hilum.sso.mapper.ClientMapper;
import com.hilum.sso.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientMapper clientMapper;
    public Client getServiceByClientId(String clientId) {
        return clientMapper.selectByClientId(clientId);
    }
}
