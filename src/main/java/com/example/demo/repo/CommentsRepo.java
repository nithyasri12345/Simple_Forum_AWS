package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Long> {
    List<Comments> findByPostId(Long postId);
}

