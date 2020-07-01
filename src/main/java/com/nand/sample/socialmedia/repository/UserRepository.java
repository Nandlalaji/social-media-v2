package com.nand.sample.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nand.sample.socialmedia.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findByFollowers(User user);
	
	User findByEmail(String email);
	
	@EntityGraph(attributePaths  = {"posts","followers"})
	List<User> findAll();

}
