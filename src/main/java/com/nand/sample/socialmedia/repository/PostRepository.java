package com.nand.sample.socialmedia.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;

public interface PostRepository extends CrudRepository<Post, Integer>{
	
	List<Post> findByUserOrderByDateTimeDesc(User user);

}
