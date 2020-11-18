package com.example.xiangfengtea.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_state")
public class OrderState {
    @Id
    @Column(nullable = false)
    private int orderStateId;
    @Column(nullable = false,length = 10)
    private String orderStateName;

    public int getOrderStateId() {
        return orderStateId;
    }

    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }

    public String getOrderStateName() {
        return orderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }
}
