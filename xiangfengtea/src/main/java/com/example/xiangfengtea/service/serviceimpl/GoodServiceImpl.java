package com.example.xiangfengtea.service.serviceimpl;

import com.example.xiangfengtea.dao.CommentDao;
import com.example.xiangfengtea.dao.GoodInfoDao;
import com.example.xiangfengtea.dao.GoodTypeDao;
import com.example.xiangfengtea.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodInfoDao goodInfoDao;
    @Autowired
    private GoodTypeDao goodTypeDao;
    @Autowired
    private CommentDao commentDao;
}
