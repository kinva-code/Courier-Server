package com.example.xiangfengtea.service.serviceimpl;

import com.example.xiangfengtea.dao.ManagerDao;
import com.example.xiangfengtea.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;
}
