package com.example.xiangfengtea.service;

import com.example.xiangfengtea.entity.ReceiveInfo;
import com.example.xiangfengtea.entity.User;

import java.util.List;

public interface UserService {
    User findUserByUserId(Long id);
    User findUserByUserEmail(String email);
    User findUserByUserName(String name);
    User saveUser(User user);
    ReceiveInfo saveReceiveInfo(ReceiveInfo receiveInfo);
    List<ReceiveInfo> findReceiveInfoByUserId(Long userId);
    void deleteReceiveInfoByReceiveInfoId(Long receiveInfoId);
}
