package com.nand.sample.socialmedia.testdata;

import com.nand.sample.socialmedia.model.User;

public class UserTestData {
	
	public static User getUser1(){
		User u = new User();
		u.setUserId(1);
		u.setName("Nand");
		u.setEmail("nand@xyz.com");
		u.setPassword("password");
		return u;
	}
	
	public static User getUser2(){
		User u1 = new User();
		u1.setUserId(2);
		u1.setName("Nand11");
		u1.setEmail("nand11@xyz.com");
		return u1;
	}
	
	public static User getUser3(){
		User u2 = new User();
		u2.setUserId(3);
		u2.setName("Nand22");
		u2.setEmail("nand22@xyz.com");
		return u2;
	}
}
