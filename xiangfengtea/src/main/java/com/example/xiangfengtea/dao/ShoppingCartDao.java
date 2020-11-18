package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartDao extends JpaRepository<ShoppingCart,Long> {
}
