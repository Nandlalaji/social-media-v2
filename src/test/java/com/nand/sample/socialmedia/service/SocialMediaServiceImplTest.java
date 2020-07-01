package com.nand.sample.socialmedia.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.nand.sample.socialmedia.exception.SocialMediaDAOException;
import com.nand.sample.socialmedia.exception.SocialMediaRuntimeException;
import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;
import com.nand.sample.socialmedia.repository.PostRepository;
import com.nand.sample.socialmedia.repository.UserRepository;
import com.nand.sample.socialmedia.service.SocialMediaServiceImpl;
import com.nand.sample.socialmedia.testdata.PostsTestData;
import com.nand.sample.socialmedia.testdata.UserTestData;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SocialMediaServiceImplTest {

	@InjectMocks
	SocialMediaServiceImpl socialMediaService;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	PostRepository postRepo;
	
	@Before
	public void init() {
	}

	@Test
	public void testGetUserNewsFeed() throws Exception {
		User user = new User();
		when(postRepo.findByUserOrderByDateTimeDesc(user)).thenReturn(PostsTestData.getposts());
		assertTrue(socialMediaService.getUserNewsFeed(user).getPosts().size()>0);
	}
	
	@Test(expected=SocialMediaDAOException.class)
	public void testGetUserNewsFeedDAOException() {
		User user = new User();
		Mockito.doThrow(new DataAccessException("..."){
			private static final long serialVersionUID = 1L; }).when(postRepo).findByUserOrderByDateTimeDesc(user);
		socialMediaService.getUserNewsFeed(user);
	}
	
	@Test(expected=SocialMediaRuntimeException.class)
	public void testGetUserNewsFeedRuntimeException() {
		when(postRepo.findByUserOrderByDateTimeDesc(new User())).thenThrow(RuntimeException.class);
		socialMediaService.getUserNewsFeed(new User());
	}
	
	@Test
	public void testGetHomeNewsFeed() throws Exception {
		Set<User> followers= new HashSet<>();
		followers.add(UserTestData.getUser1());
		User user = new User();
		user.setFollowers(followers);
		when(postRepo.findByUserOrderByDateTimeDesc(user)).thenReturn(PostsTestData.getposts());
		assertTrue(socialMediaService.getHomeNewsFeed(user).getPosts().size()>0);
	}
	
	@Test(expected=SocialMediaDAOException.class)
	public void testGetHomeNewsFeedDAOException() {
		User user = new User();
		Mockito.doThrow(new DataAccessException("..."){
			private static final long serialVersionUID = 1L; }).when(postRepo).findByUserOrderByDateTimeDesc(user);
		socialMediaService.getHomeNewsFeed(user);
	}
	@Test(expected=SocialMediaRuntimeException.class)
	public void testGetHomeNewsFeedRuntimeException() {
		User user = new User();
		when(postRepo.findByUserOrderByDateTimeDesc(user)).thenReturn(PostsTestData.getposts());
		socialMediaService.getHomeNewsFeed(user);
	}

	@Test
	public void testfollow() throws Exception {
		Set<User> followers= new HashSet<>();
		followers.add(UserTestData.getUser2());
		User user = UserTestData.getUser1();
		user.setFollowers(followers);
		User follower = UserTestData.getUser3();
		when(userRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user),Optional.of(follower));
		Set<User> followersResult = socialMediaService.follow(user, follower);
		assertTrue(followersResult.contains(follower));
	}
	@Test(expected=SocialMediaDAOException.class)
	public void testfollowDAOException() {
		Set<User> followers= new HashSet<>();
		followers.add(UserTestData.getUser2());
		User user = UserTestData.getUser1();
		user.setFollowers(followers);
		User follower = UserTestData.getUser3();
		when(userRepo.findById(Mockito.any(Integer.class))).thenThrow(new DataAccessException("..."){
			private static final long serialVersionUID = 1L; });
		socialMediaService.follow(user, follower);
	}
	@Test(expected=SocialMediaRuntimeException.class)
	public void testfollowRuntimeException() {
		User user = UserTestData.getUser1();
		User follower = UserTestData.getUser3();
		when(userRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user),Optional.of(follower));
		socialMediaService.follow(user, follower);
	}

	@Test
	public void testUnfollow() throws Exception {
		Set<User> followers= new HashSet<>();
		followers.add(UserTestData.getUser2());
		User user = UserTestData.getUser1();
		user.setFollowers(followers);
		User follower = UserTestData.getUser2();
		when(userRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
		Set<User> followersResult = socialMediaService.unfollow(user, follower);
		assertFalse(followersResult.contains(follower));
	}
	
	@Test(expected=SocialMediaDAOException.class)
	public void testUnfollowDAOException() throws Exception {
		when(userRepo.findById(Mockito.any(Integer.class))).thenThrow(new DataAccessException("..."){
			private static final long serialVersionUID = 1L; });
		socialMediaService.unfollow(UserTestData.getUser1(), UserTestData.getUser2());
	}
	
	@Test(expected=SocialMediaRuntimeException.class)
	public void testUnfollowRuntimeException() throws Exception {
		when(userRepo.findById(Mockito.any(Integer.class))).thenThrow(RuntimeException.class);
		socialMediaService.unfollow(UserTestData.getUser1(), UserTestData.getUser2());
	}

	@Test
	public void testCreatePost() throws Exception {
		User user = new User();
		user.setName("Nand");
		when(userRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(user));
		when(postRepo.save(Mockito.any(Post.class))).thenReturn(null);
		socialMediaService.createPost(1, "anyContent");
	}
	@Test(expected=SocialMediaDAOException.class)
	public void testCreatePostDAOException(){
		when(userRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(new User()));
		Mockito.doThrow(new DataAccessException("..."){
			private static final long serialVersionUID = 1L; }).when(postRepo).save(Mockito.any(Post.class));
		socialMediaService.createPost(1, "anyContent");
	}
	@Test(expected=SocialMediaRuntimeException.class)
	public void testCreatePostRuntimeException() {
		when(userRepo.findById(Mockito.any(Integer.class))).thenThrow(RuntimeException.class);
		when(postRepo.save(Mockito.any(Post.class))).thenReturn(null);
		socialMediaService.createPost(1, "anyContent");
	}
}
