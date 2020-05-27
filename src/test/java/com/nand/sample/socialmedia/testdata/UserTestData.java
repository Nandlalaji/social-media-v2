package com.nand.sample.socialmedia.testdata;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.nand.sample.socialmedia.model.User;

public class UserTestData {
	
	public static TreeSet<User> getUser(){
		TreeSet<User> userList = new TreeSet<>();
		List<Integer> followers = new ArrayList<>();
		
		followers.add(2);
		followers.add(3);
		
		User u = new User();
		u.setUserId(1);
		u.setName("Nand");
		u.setEmail("nand@xyz.com");
	//	u.setFollowers(followers);
		userList.add(u);
		
		
		List<Integer> followers1 = new ArrayList<>();
		followers1.add(3);
		
		User u1 = new User();
		u1.setUserId(2);
		u1.setName("Nand11");
		u1.setEmail("nand11@xyz.com");
	//	u1.setFollowers(followers1);
		
		userList.add(u1);
		
		User u2 = new User();
		u2.setUserId(3);
		u2.setName("Nand22");
		u2.setEmail("nand22@xyz.com");
		u2.setFollowers(null);
		
		userList.add(u2);
		
		return userList;
	}

}
