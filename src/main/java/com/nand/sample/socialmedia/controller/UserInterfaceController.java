package com.nand.sample.socialmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nand.sample.socialmedia.domain.Followers;
import com.nand.sample.socialmedia.domain.Posts;
import com.nand.sample.socialmedia.domain.Users;
import com.nand.sample.socialmedia.exception.SocialMediaDAOException;
import com.nand.sample.socialmedia.exception.SocialMediaRuntimeException;
import com.nand.sample.socialmedia.model.User;
import com.nand.sample.socialmedia.service.SocialMediaService;

/**
 * This is controller class to interact with user it has used SocialMediaService
 * to all operation
 * 
 * @author Nand
 * @since 23th Sept 2019
 */
@RestController
public class UserInterfaceController {

	SocialMediaService socialMediaService;

	/**
	 * set SocialMediaService via autowiring
	 * 
	 * @param post
	 */
	@Autowired
	public void setSocialMediaService(SocialMediaService socialMediaService) {
		this.socialMediaService = socialMediaService;
	}

	/**
	 * Post method to add new user
	 * 
	 * @param user
	 */
	@PostMapping(path = "/inputNewUser", consumes = "application/json")
	public void addNewUser(@RequestBody User user) {
		try {
			this.socialMediaService.setusers(user);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"DB Exception - " + ex.getMessage(), ex);
		}
	}

	/**
	 * Delete method to remove user
	 * 
	 * @param userId
	 */
	@DeleteMapping(path = "/removeUser/{userId}", consumes = "application/json")
	public void removeUser(@PathVariable Integer userId) {
		try {
			this.socialMediaService.getUserByUserID(userId);
			this.socialMediaService.removeUser(userId);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"DB Exception - " + ex.getMessage(), ex);
		}
	}

	/**
	 * Post method to add new post
	 * 
	 * @param userId
	 * @param content
	 */
	@PostMapping(path = "/inputNewPost/{userId}/{content}", consumes = "application/json")
	public void addNewPost(@PathVariable Integer userId,
			@PathVariable String content) {
		try {
			this.socialMediaService.getUserByUserID(userId);
			this.socialMediaService.createPost(userId, content);
		} catch (SocialMediaDAOException | SocialMediaRuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception occured while adding new post - "
							+ ex.getMessage(), ex);
		}
	}

	/**
	 * Delete method to remove post
	 * 
	 * @param postId
	 */
	@DeleteMapping(path = "/removePost/{postId}", consumes = "application/json")
	public void removePost(@PathVariable Integer postId) {
		try {
			this.socialMediaService.getPostByPostID(postId);
			this.socialMediaService.removePost(postId);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"DB Exception - " + ex.getMessage(), ex);
		}
	}

	/**
	 * Post method to make existing user follow other user
	 * 
	 * @param followeeId
	 * @param followerId
	 */
	@PostMapping(path = "/follow/{followeeId}/{followerId}", consumes = "application/json")
	public void follow(@PathVariable Integer followeeId,
			@PathVariable Integer followerId) {
		try {
			User followee = this.socialMediaService.getUserByUserID(followeeId);
			User follower = this.socialMediaService.getUserByUserID(followerId);
			this.socialMediaService.follow(followee, follower);
		} catch (SocialMediaDAOException | SocialMediaRuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception occured in follow - " + ex.getMessage(), ex);
		}
	}

	/**
	 * Delete method to make existing user unfollow other user
	 * 
	 * @param followeeId
	 * @param followerId
	 */
	@DeleteMapping(path = "/unfollow/{followeeId}/{unfollowerId}", consumes = "application/json")
	public void unfollow(@PathVariable Integer followeeId,
			@PathVariable Integer unfollowerId) {
		try {
			User followee = this.socialMediaService.getUserByUserID(followeeId);
			User unfollower = this.socialMediaService
					.getUserByUserID(unfollowerId);
			this.socialMediaService.unfollow(followee, unfollower);
		} catch (SocialMediaDAOException | SocialMediaRuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception occured in unfollow - " + ex.getMessage(), ex);
		}
	}

	/**
	 * get method to show follower of an existing user
	 * 
	 * @param followeeId
	 * @param followerId
	 */
	@GetMapping("/getFollowers/{followeeId}")
	public Followers getfollower(@PathVariable Integer followeeId) {
		try {
			return this.socialMediaService.getFollowers(followeeId);
		} catch (SocialMediaDAOException | SocialMediaRuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception occured in getting user feed - "
							+ ex.getMessage(), ex);
		}
	}

	/**
	 * Get method to get related User posts only for given particular user
	 * 
	 * @param userId
	 */
	@GetMapping("/getUserNewFeed/{userId}")
	public Posts getUserNewsFeed(@PathVariable Integer userId) {
		try {
			User user = this.socialMediaService.getUserByUserID(userId);
			return this.socialMediaService.getUserNewsFeed(user);
		} catch (SocialMediaDAOException | SocialMediaRuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception occured in getting user feed - "
							+ ex.getMessage(), ex);
		}
	}

	/**
	 * Get method to get related home posts for given particular user
	 * 
	 * @param userId
	 */
	@GetMapping("/getHomeNewFeed/{userId}")
	public Posts getHomeNewsFeed(@PathVariable Integer userId) {
		try {
			User user = this.socialMediaService.getUserByUserID(userId);
			return this.socialMediaService.getHomeNewsFeed(user);
		} catch (SocialMediaDAOException | SocialMediaRuntimeException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exception occured in getting home feed - "
							+ ex.getMessage(), ex);
		}
	}

	/**
	 * Get method to get allUser
	 * 
	 */
	@GetMapping("/getAllUser")
	public Users getAllUser() {
		try {
			return this.socialMediaService.getAllUsers();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"DB Exception - " + ex.getMessage(), ex);
		}
	}

	/**
	 * Get method to get post
	 * 
	 */
	@GetMapping("/getAllPost")
	public Posts getAllPost() {
		try {
			return this.socialMediaService.getAllPost();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"DB Exception - " + ex.getMessage(), ex);
		}
	}
}
