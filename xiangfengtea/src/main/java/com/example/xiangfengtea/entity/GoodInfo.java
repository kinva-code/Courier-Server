package com.example.xiangfengtea.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "good_info")
@Table(indexes = {@Index(columnList = "goodName"),@Index(columnList = "goodTypeId"),@Index(columnList = "goodOriginPlace")})
public class GoodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodId;
    @Column(nullable = false,length = 10,unique = true)
    private String goodName;
    @Column(nullable = false)
    private int goodTypeId;
    @Column(nullable = false)
    private BigDecimal goodPrice;
    @Column(nullable = false)
    private Long goodStock;
    @Column(nullable = false)
    private String goodOriginPlace;
    @Column(nullable = false)
    private String goodYear;
    @Column(nullable = false)
    private String goodLaunchTime;
    @Column(nullable = false)
    private String goodPicture;
    @Column(nullable = false)
    private Long goodPurchaseNum;
    @Column(nullable = false)
    private Long goodSoldNum;
    @Column(nullable = false)
    private Long goodCommentNum;
    @Column(nullable = false)
    private String goodIntroduce;
    @Column(nullable = false)
    private String goodDetailInfo;

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

    public int getGoodTypeId() {
        return goodTypeId;
    }

    public void setGoodTypeId(int goodTypeId) {
        this.goodTypeId = goodTypeId;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Long getGoodStock() {
        return goodStock;
    }

    public void setGoodStock(Long goodStock) {
        this.goodStock = goodStock;
    }

    public String getGoodOriginPlace() {
        return goodOriginPlace;
    }

    public void setGoodOriginPlace(String goodOriginPlace) {
        this.goodOriginPlace = goodOriginPlace;
    }

    public String getGoodYear() {
        return goodYear;
    }

    public void setGoodYear(String goodYear) {
        this.goodYear = goodYear;
    }

    public String getGoodLaunchTime() {
        return goodLaunchTime;
    }

    public void setGoodLaunchTime(String goodLaunchTime) {
        this.goodLaunchTime = goodLaunchTime;
    }

    public String getGoodPicture() {
        return goodPicture;
    }

    public void setGoodPicture(String goodPicture) {
        this.goodPicture = goodPicture;
    }

    public Long getGoodPurchaseNum() {
        return goodPurchaseNum;
    }

    public void setGoodPurchaseNum(Long goodPurchaseNum) {
        this.goodPurchaseNum = goodPurchaseNum;
    }

    public Long getGoodSoldNum() {
        return goodSoldNum;
    }

    public void setGoodSoldNum(Long goodSoldNum) {
        this.goodSoldNum = goodSoldNum;
    }

    public Long getGoodCommentNum() {
        return goodCommentNum;
    }

    public void setGoodCommentNum(Long goodCommentNum) {
        this.goodCommentNum = goodCommentNum;
    }

    public String getGoodIntroduce() {
        return goodIntroduce;
    }

    public void setGoodIntroduce(String goodIntroduce) {
        this.goodIntroduce = goodIntroduce;
    }

    public String getGoodDetailInfo() {
        return goodDetailInfo;
    }

    public void setGoodDetailInfo(String goodDetailInfo) {
        this.goodDetailInfo = goodDetailInfo;
    }
}
