package com.example.xiangfengtea.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "comment")
@Table(indexes = {@Index(columnList = "goodOrderId"),@Index(columnList = "userId"),@Index(columnList = "goodId")})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false)
    private Long goodOrderId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long goodId;
    @Column(nullable = false)
    private String commentContent;
    @Column(nullable = false)
    private String commentTime;
    @Column(nullable = false)
    private String commentPicture;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(String commentPicture) {
        this.commentPicture = commentPicture;
    }
}
