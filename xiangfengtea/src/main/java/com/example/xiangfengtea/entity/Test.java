package com.example.xiangfengtea.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 10)
    private BigDecimal goodPrice;
    private Timestamp goodLaunchTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Timestamp getGoodLaunchTime() {
        return goodLaunchTime;
    }

    public void setGoodLaunchTime(Timestamp goodLaunchTime) {
        this.goodLaunchTime = goodLaunchTime;
    }
}
