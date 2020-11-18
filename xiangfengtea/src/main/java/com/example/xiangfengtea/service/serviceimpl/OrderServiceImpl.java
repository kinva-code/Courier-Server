package com.example.xiangfengtea.service.serviceimpl;

import com.example.xiangfengtea.dao.OrderDao;
import com.example.xiangfengtea.dao.OrderInfoDao;
import com.example.xiangfengtea.dao.OrderStateDao;
import com.example.xiangfengtea.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private OrderStateDao orderStateDao;
}
