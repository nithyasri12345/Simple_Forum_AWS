package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
	
	    List<Post> findAllByOrderByIdDesc(); 
	    List<Post> findByTitleContainingIgnoreCaseOrderByIdDesc(String title);

	

}

