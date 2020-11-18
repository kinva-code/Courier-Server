package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.GoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<GoodOrder,Long> {
}
