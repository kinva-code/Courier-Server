package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.ReceiveInfo;
import com.example.xiangfengtea.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ReceiveInfoDao extends JpaRepository<ReceiveInfo,Long> {
    List<ReceiveInfo> findReceiveInfoByUserId(Long userId);
    @Transactional
    void deleteReceiveInfoByReceiveInfoId(Long receiveInfoId);
}