package com.nand.sample.socialmedia.testdata;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.nand.sample.socialmedia.model.Post;

public class PostsTestData {
	public static List<Post> getposts() {
		
		List<Post> allPost = new ArrayList<>();

		Post p1 = new Post();
		p1.setContent("post1");
		p1.setPostId(1);
		p1.setUser(UserTestData.getUser1());
		p1.setDateTime(LocalDateTime.of(2018, Month.JULY, 29, 19, 30, 40));

		Post p2 = new Post();
		p2.setContent("post2");
		p2.setPostId(2);
		p2.setUser(UserTestData.getUser2());
		p2.setDateTime(LocalDateTime.of(2019, Month.AUGUST, 29, 19, 30, 40));

		Post p3 = new Post();
		p3.setContent("post3");
		p3.setPostId(3);
		p3.setUser(UserTestData.getUser3());
		p3.setDateTime(LocalDateTime.of(2019, Month.AUGUST, 29, 19, 00, 40));

		Post p4 = new Post();
		p4.setContent("post4");
		p4.setPostId(4);
		p4.setUser(UserTestData.getUser1());
		p4.setDateTime(LocalDateTime.of(2018, Month.SEPTEMBER, 29, 19, 30, 40));

		Post p5 = new Post();
		p5.setContent("post5");
		p5.setPostId(5);
		p5.setUser(UserTestData.getUser2());
		p5.setDateTime(LocalDateTime.of(2018, Month.AUGUST, 2, 19, 30, 40));

		Post p6 = new Post();
		p6.setContent("post6");
		p6.setPostId(6);
		p6.setUser(UserTestData.getUser3());
		p6.setDateTime(LocalDateTime.of(2017, Month.AUGUST, 29, 1, 30, 40));

		Post p7 = new Post();
		p7.setContent("post7");
		p7.setPostId(7);
		p7.setUser(UserTestData.getUser3());
		p7.setDateTime(LocalDateTime.of(2019, Month.JANUARY, 14, 19, 30, 40));

		allPost.add(p1);
		allPost.add(p2);
		allPost.add(p3);
		allPost.add(p4);
		allPost.add(p5);
		allPost.add(p6);
		allPost.add(p7);

		return allPost;
	}
}
