package com.example.xiangfengtea.entity;

import javax.persistence.*;

@Entity(name = "shopping_cart")
@Table(indexes = {@Index(columnList = "userId")})
public class ShoppingCart {
    @Id
    @Column(nullable = false)
    private Long shoppingCartId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long goodId;
    @Column(nullable = false)
    private int goodNum;

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }
}
