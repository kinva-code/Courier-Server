package com.example.xiangfengtea.entity;

import javax.persistence.*;

@Entity(name = "receive_info")
@Table(indexes = {@Index(columnList = "userId")})
public class ReceiveInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiveInfoId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String phoneNum;
    @Column(nullable = false)
    private String addressInfo;
    @Column(nullable = false)
    private String receiveAddress;
    @Column(nullable = false,length = 20)
    private String receiveName;

    public Long getReceiveInfoId() {
        return receiveInfoId;
    }

    public void setReceiveInfoId(Long receiveInfoId) {
        this.receiveInfoId = receiveInfoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
}
