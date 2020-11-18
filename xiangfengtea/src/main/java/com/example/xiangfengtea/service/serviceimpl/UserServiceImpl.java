package com.example.xiangfengtea.service.serviceimpl;

import com.example.xiangfengtea.dao.ReceiveInfoDao;
import com.example.xiangfengtea.dao.ShoppingCartDao;
import com.example.xiangfengtea.dao.UserDao;
import com.example.xiangfengtea.entity.ReceiveInfo;
import com.example.xiangfengtea.entity.User;
import com.example.xiangfengtea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private ReceiveInfoDao receiveInfoDao;

    @Override
    public User findUserByUserId(Long id) {
        return userDao.findUserByUserId(id);
    }

    @Override
    public User findUserByUserEmail(String email) {
        return userDao.findUserByUserEmail(email);
    }

    @Override
    public User findUserByUserName(String name) {
        return userDao.findUserByUserName(name);
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public ReceiveInfo saveReceiveInfo(ReceiveInfo receiveInfo) {
        return receiveInfoDao.save(receiveInfo);
    }

    @Override
    public List<ReceiveInfo> findReceiveInfoByUserId(Long userId) {
        return receiveInfoDao.findReceiveInfoByUserId(userId);
    }

    @Override
    public void deleteReceiveInfoByReceiveInfoId(Long receiveInfoId) {
        receiveInfoDao.deleteReceiveInfoByReceiveInfoId(receiveInfoId);
    }
}
