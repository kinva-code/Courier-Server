package com.example.xiangfengtea.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "good_order")
@Table(indexes = {@Index(columnList = "goodOrderId"),@Index(columnList = "userId")})
public class GoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodOrderSerialId;
    @Column(nullable = false,unique = true)
    private Long goodOrderId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false,length = 20)
    private String userName;
    @Column(nullable = false)
    private String goodOrderCreateTime;
    @Column()
    private String goodOrderPayTime;
    @Column(nullable = false)
    private BigDecimal goodOrderTotalPrice;
    @Column(nullable = false,length = 20)
    private String receiveName;
    @Column(nullable = false,length = 40)
    private String receiveAddress;
    @Column(nullable = false)
    private String phoneNum;
    @Column(nullable = false)
    private int orderStateId;
    @Column()
    private String goodOrderRemark;

    public Long getGoodOrderSerialId() {
        return goodOrderSerialId;
    }

    public void setGoodOrderSerialId(Long goodOrderSerialId) {
        this.goodOrderSerialId = goodOrderSerialId;
    }

    public Long getGoodOrderId() {
        return goodOrderId;
    }

    public void setGoodOrderId(Long goodOrderId) {
        this.goodOrderId = goodOrderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodOrderCreateTime() {
        return goodOrderCreateTime;
    }

    public void setGoodOrderCreateTime(String goodOrderCreateTime) {
        this.goodOrderCreateTime = goodOrderCreateTime;
    }

    public String getGoodOrderPayTime() {
        return goodOrderPayTime;
    }

    public void setGoodOrderPayTime(String goodOrderPayTime) {
        this.goodOrderPayTime = goodOrderPayTime;
    }

    public BigDecimal getGoodOrderTotalPrice() {
        return goodOrderTotalPrice;
    }

    public void setGoodOrderTotalPrice(BigDecimal goodOrderTotalPrice) {
        this.goodOrderTotalPrice = goodOrderTotalPrice;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getOrderStateId() {
        return orderStateId;
    }

    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }

    public String getGoodOrderRemark() {
        return goodOrderRemark;
    }

    public void setGoodOrderRemark(String goodOrderRemark) {
        this.goodOrderRemark = goodOrderRemark;
    }
}
