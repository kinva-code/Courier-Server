package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findUserByUserEmail(String email);
    User findUserByUserName(String name);
    User findUserByUserId(Long id);
}
