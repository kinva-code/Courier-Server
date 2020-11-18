package com.example.xiangfengtea.dao;

import com.example.xiangfengtea.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment,Long> {
}
