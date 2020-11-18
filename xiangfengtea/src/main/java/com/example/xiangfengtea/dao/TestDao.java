package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TestDao extends JpaRepository<Test,Long> {
    public Test findByGoodPrice(BigDecimal goodPrice);

    /**
     * select t from test t where t.goodPrice=:goodPrice
     * test为表名
     * @param goodPrice
     * @return
     */
    @Query("select t from test t where t.goodPrice=:goodPrice")
    List<Test> findGoodPice(@Param("goodPrice")BigDecimal goodPrice);

    @Query(value = "select * from test where good_price=:goodPrice",nativeQuery = true)
    List<Test> findGoodPrice2(@Param("goodPrice")BigDecimal goodPrice);
}
