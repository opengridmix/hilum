package com.hilum.demo.dataaccess.repository;

import com.hilum.demo.dataaccess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
