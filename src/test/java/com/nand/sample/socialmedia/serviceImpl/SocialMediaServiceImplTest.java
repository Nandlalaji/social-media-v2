package com.nand.sample.socialmedia.serviceImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;
import com.nand.sample.socialmedia.service.SocialMediaServiceImpl;
//import com.nand.sample.socialmedia.service.PostService;
import com.nand.sample.socialmedia.testdata.PostUserMappingTestData;
import com.nand.sample.socialmedia.testdata.UserTestData;

import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SocialMediaServiceImplTest {

	@InjectMocks
	SocialMediaServiceImpl post;
	
	SocialMediaServiceImpl postTemp;
	
	@Before
	public void init() {
		postTemp = mock(SocialMediaServiceImpl.class);
	}

	@Test
	public void getNewsFeedTest() throws Exception {
		/*//when(postTemp.getPostUserMapping()).thenReturn(PostUserMappingTestData.getpostUserMapping());

		when(postTemp.getAllUsers()).thenReturn(UserTestData.getUser());
		//List<String> contents = post.getNewsFeed(2);
		contents.forEach(k -> System.out.println(k));
		assertTrue(contents.contains("post7"));

		contents = post.getNewsFeed(3);
		contents.forEach(k -> System.out.println(k));
		assertFalse(contents.contains("post2"));*/
	}

	@Test
	public void followTest() throws Exception {

		/*// check for inserted follower
		List<Integer> followers = null;
		when(postTemp.getAllUsers()).thenReturn(UserTestData.getUser());
		int followerSizeFromTempUser = 0;
		TreeSet<User> users = post.follow(1, 2);
		for (User user : users) {
			if (user.getUserId() == 2) {
				followers = user.getFollowers();
				followerSizeFromTempUser = user.getFollowers().size();
			}
		}
		assertTrue(followers.contains(1));

		// check if duplication does not happen
		TreeSet<User> users1 = post.follow(1, 2);
		int followerSize = 0;
		for (User user : users1) {
			if (user.getUserId() == 2) {
				followerSize = user.getFollowers().size();
			}
		}
		assertEquals(followerSize, followerSizeFromTempUser);*/
	}

	@Test
	public void unfollowTest() throws Exception {
		/*// check for inserted follower
		List<Integer> followers = null;
		when(postTemp.getAllUsers()).thenReturn(UserTestData.getUser());
		int followerSizeFromTempUser = 0;
		TreeSet<User> users = post.unfollow(2, 1);
		for (User user : users) {
			if (user.getUserId() == 1) {
				followers = user.getFollowers();
				followerSizeFromTempUser = user.getFollowers().size();
			}
		}
		assertTrue(!followers.contains(2));

		// check if duplication does not happen
		TreeSet<User> users1 = post.unfollow(2, 1);
		int followerSize = 0;
		for (User user : users1) {
			if (user.getUserId() == 1) {
				followerSize = user.getFollowers().size();
			}
		}
		assertEquals(followerSize, followerSizeFromTempUser);*/

	}

	@Test
	public void createPostTest() throws Exception {
		/*when(postTemp.getPostUserMapping()).thenReturn(PostUserMappingTestData.getpostUserMapping());

		Map<Post, Integer> tposts = post.createPost(10, 10, "post10");
		assertTrue(tposts.size() == 8);*/
	}
}
