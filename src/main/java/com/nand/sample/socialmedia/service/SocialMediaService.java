package com.nand.sample.socialmedia.service;

import java.util.Set;

import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;

/**
 * Interface to hold method related to simple social media 
 * 
 * @author Nand
 * @since 23th Sept 2019
 */
public interface SocialMediaService {
	/**
	 * Creates new post for particular user
	 * 
	 * @param userId
	 * @param postId
	 * @param content
	 * @return
	 * @throws Exception
	 */
	void createPost(Integer userId, String content) throws Exception;

	/**
	 * Get New Feed which user has posted recently.
	 * Posts are sorted with date it posted
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	String getUserNewsFeed(User userTogetNew) throws Exception;
	
	/**
	 * Get New Feed which user and its follower has posted recently.
	 * Posts are sorted with date it posted
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	String getHomeNewsFeed(User userTogetNew) throws Exception;

	/**
	 * User can follow any user
	 * 
	 * @param followerId
	 * @param followeeId
	 * @return
	 * @throws Exception
	 */
	Set<User>  follow(User followee, User follower) throws Exception;

	/**
	 * User can unfollow any user
	 * 
	 * @param followerId
	 * @param followeeId
	 * @return
	 * @throws Exception
	 */
	Set<User> unfollow(User followee, User follower) throws Exception;

	/**
	 * User id is assigned via logic and is not input by user
	 * creates new user
	 * 
	 * @param user
	 */
	void setusers(User user);

	/**
	 * get all users
	 * @return
	 */
	Iterable<User> getAllUsers();

	/**
	 * get all post
	 * @return
	 */
	Iterable<Post> getAllPost();
	
	/**
	 * get user object by userId
	 * @return
	 */
	
	User getUserByUserID(Integer userId);
}
