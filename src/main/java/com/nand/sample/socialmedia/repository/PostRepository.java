package com.nand.sample.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findByUserOrderByDateTimeDesc(User user);

}
