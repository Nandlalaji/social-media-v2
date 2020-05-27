package com.nand.sample.socialmedia.testdata;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import com.nand.sample.socialmedia.model.Post;

public class PostUserMappingTestData {
	public static Map<Post, Integer> getpostUserMapping() {
		Map<Post, Integer> allPost = new HashMap<>();

		Post p1 = new Post();
		p1.setContent("post1");
		p1.setPostId(1);
		p1.setDateTime(LocalDateTime.of(2018, Month.JULY, 29, 19, 30, 40));

		Post p2 = new Post();
		p2.setContent("post2");
		p2.setPostId(2);
		p2.setDateTime(LocalDateTime.of(2019, Month.AUGUST, 29, 19, 30, 40));

		Post p3 = new Post();
		p3.setContent("post3");
		p3.setPostId(3);
		p3.setDateTime(LocalDateTime.of(2019, Month.AUGUST, 29, 19, 00, 40));

		Post p4 = new Post();
		p4.setContent("post4");
		p4.setPostId(4);
		p4.setDateTime(LocalDateTime.of(2018, Month.SEPTEMBER, 29, 19, 30, 40));

		Post p5 = new Post();
		p5.setContent("post5");
		p5.setPostId(5);
		p5.setDateTime(LocalDateTime.of(2018, Month.AUGUST, 2, 19, 30, 40));

		Post p6 = new Post();
		p6.setContent("post6");
		p6.setPostId(6);
		p6.setDateTime(LocalDateTime.of(2017, Month.AUGUST, 29, 1, 30, 40));

		Post p7 = new Post();
		p7.setContent("post7");
		p7.setPostId(7);
		p7.setDateTime(LocalDateTime.of(2019, Month.JANUARY, 14, 19, 30, 40));

		allPost.put(p1, 2);
		allPost.put(p2, 2);
		allPost.put(p3, 1);
		allPost.put(p4, 1);
		allPost.put(p5, 3);
		allPost.put(p6, 2);
		allPost.put(p7, 3);

		return allPost;
	}
}
