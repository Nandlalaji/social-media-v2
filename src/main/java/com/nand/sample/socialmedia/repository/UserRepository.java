package com.nand.sample.socialmedia.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.nand.sample.socialmedia.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	List<User> findByFollowers(User user);
	
	User findByEmail(String email);

}
