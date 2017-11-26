package com.hilum.demo.service;
import com.hilum.demo.dataaccess.entity.User;
import com.hilum.demo.dataaccess.repository.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    SnowFlakeIdGenerator idGenerator;

    @CachePut(key = "#p0.id.toString()")
    public User insert(User user) {
        user.setId(idGenerator.nextId());
        return userRepository.save(user);
    }

    @CachePut(key = "#p0.id.toString()")
    public User update(User user) {
        return userRepository.save(user);
    }

    @Cacheable(key = "#p0.toString()")
    public User select(Long id) {
        return userRepository.findOne(id);
    }

    @CacheEvict(key = "#p0.toString()")
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
