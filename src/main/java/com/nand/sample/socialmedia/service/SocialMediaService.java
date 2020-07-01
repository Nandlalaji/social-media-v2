package com.nand.sample.socialmedia.service;

import java.util.Set;

import com.nand.sample.socialmedia.domain.Followers;
import com.nand.sample.socialmedia.domain.Posts;
import com.nand.sample.socialmedia.domain.Users;
import com.nand.sample.socialmedia.exception.SocialMediaDAOException;
import com.nand.sample.socialmedia.exception.SocialMediaRuntimeException;
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
	void createPost(Integer userId, String content) throws SocialMediaDAOException,SocialMediaRuntimeException;

	/**
	 * Get New Feed which user has posted recently. Posts are sorted with date
	 * it posted
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Posts getUserNewsFeed(User userTogetNew) throws SocialMediaDAOException,SocialMediaRuntimeException;

	/**
	 * Get New Feed which user and its follower has posted recently. Posts are
	 * sorted with date it posted
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Posts getHomeNewsFeed(User userTogetNew) throws SocialMediaDAOException,SocialMediaRuntimeException;

	/**
	 * User can follow any user
	 * 
	 * @param followerId
	 * @param followeeId
	 * @return
	 * @throws Exception
	 */
	Set<User> follow(User followee, User follower) throws SocialMediaDAOException,SocialMediaRuntimeException;

	/**
	 * User can unfollow any user
	 * 
	 * @param followerId
	 * @param followeeId
	 * @return
	 * @throws Exception
	 */
	Set<User> unfollow(User followee, User follower) throws SocialMediaDAOException,SocialMediaRuntimeException;

	/**
	 * User id is assigned via logic and is not input by user creates new user
	 * 
	 * @param user
	 */
	void setusers(User user) throws Exception;

	/**
	 * get all users
	 * 
	 * @return
	 */
	Users getAllUsers()throws Exception;

	/**
	 * get all post
	 * 
	 * @return
	 */
	Posts getAllPost() throws Exception;

	/**
	 * get user object by userId
	 * 
	 * @return
	 */
	User getUserByUserID(Integer userId) throws SocialMediaDAOException;
	
	/**
	 * get Post object by postId
	 * 
	 * @return
	 */
	Post getPostByPostID(Integer postId) throws Exception;
	
	/**
	 * get User follower object by followeeId
	 * 
	 * @return
	 */
	Followers getFollowers(Integer followeeId) throws SocialMediaDAOException;
	
	/**
	 * remove user object by userId
	 * 
	 * @return
	 * @throws Exception 
	 */
	void removeUser(Integer userId) throws Exception;
	
	/**
	 * remove user object by userId
	 * 
	 * @return
	 * @throws Exception 
	 */
	void removePost(Integer postId) throws Exception;

}
