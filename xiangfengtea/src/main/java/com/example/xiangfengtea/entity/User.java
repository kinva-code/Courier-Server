package com.example.xiangfengtea.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "user")
@Table(indexes = {@Index(columnList = "userName"),@Index(columnList = "userEmail")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false,length = 20,unique = true)
    private String userName;
    @Column(nullable = false,length = 20)
    private String userPassword;
    @Column()
    private int userAge;
    @Column(length = 4)
    private String userSex;
    @Column(nullable = false,length = 20,unique = true)
    private String userEmail;
    @Column(nullable = false)
    private String phoneNum;
    @Column()
    private String userHeadImage;
    @Column(nullable = false)
    private String userVipDeadline;
    @Column()
    private Long receiveInfoId;

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public String getUserVipDeadline() {
        return userVipDeadline;
    }

    public void setUserVipDeadline(String userVipDeadline) {
        this.userVipDeadline = userVipDeadline;
    }

    public Long getReceiveInfoId() {
        return receiveInfoId;
    }

    public void setReceiveInfoId(Long receiveInfoId) {
        this.receiveInfoId = receiveInfoId;
    }
}
