package com.example.xiangfengtea.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "order_info")
@Table(indexes = {@Index(columnList = "goodOrderId")})
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderInfoId;
    @Column(nullable = false)
    private Long goodOrderId;
    @Column(nullable = false)
    private Long goodId;
    @Column(nullable = false,length = 10)
    private String goodName;
    @Column(nullable = false)
    private BigDecimal goodPrice;
    @Column(nullable = false)
    private int goodNum;

    public Long getOrderInfoId() {
        return orderInfoId;
    }

    public void setOrderInfoId(Long orderInfoId) {
        this.orderInfoId = orderInfoId;
    }

    public Long getGoodOrderId() {
        return goodOrderId;
    }

    public void setGoodOrderId(Long goodOrderId) {
        this.goodOrderId = goodOrderId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }
}
