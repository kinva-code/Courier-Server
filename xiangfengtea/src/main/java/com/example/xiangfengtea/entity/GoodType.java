package com.example.xiangfengtea.entity;

import javax.persistence.*;

@Entity(name = "good_type")
public class GoodType {
    @Id
    @Column(nullable = false,unique = true)
    private int goodTypeId;
    @Column(nullable = false,unique = true)
    private String goodTypeName;

    public int getGoodTypeId() {
        return goodTypeId;
    }

    public void setGoodTypeId(int goodTypeId) {
        this.goodTypeId = goodTypeId;
    }

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }
}
