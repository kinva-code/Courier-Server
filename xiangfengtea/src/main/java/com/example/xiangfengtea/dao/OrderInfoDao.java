package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoDao extends JpaRepository<OrderInfo,Long> {
}
