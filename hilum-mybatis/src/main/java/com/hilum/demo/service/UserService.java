package com.hilum.demo.service;

import com.hilum.demo.dataaccess.mapper.UserMapper;
import com.hilum.demo.dataaccess.model.User;
import com.hilum.demo.common.utils.SnowFlakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "users")
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserMapper userMapper;

    @Autowired
    SnowFlakeIdGenerator idGenerator;

    @CachePut(key = "#p0.id.toString()")
    public User insert(User user) {
        user.setId(idGenerator.nextId());
        int inserted = userMapper.insert(user);
        return inserted == 1 ? user : null;
    }

    @CachePut(key = "#p0.id.toString()")
    public User update(User user) {
        int update = userMapper.updateByPrimaryKey(user);
        return update == 1 ? user : null;
    }

    @Cacheable(key = "#p0.toString()")
    public User select(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @CacheEvict(key = "#p0.toString()")
    public int delete(Long id) {
        int deleted = userMapper.deleteByPrimaryKey(id);
        return deleted;
    }
}
