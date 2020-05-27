package com.nand.sample.socialmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;
import com.nand.sample.socialmedia.service.SocialMediaService;

/**
 * This is controller class to interact with user it has used SocialMediaService to all
 * operation
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
		this.socialMediaService.setusers(user);
	}

	/**
	 * Post method to add new post
	 * 
	 * @param userId
	 * @param content
	 */
	@PostMapping(path = "/inputNewPost/{userId}/{content}" ,consumes = "application/json")
	public void addNewPost(@PathVariable Integer userId, @PathVariable String content) throws Exception {
		checkForUser(userId);
		this.socialMediaService.createPost(userId, content);
	}

	/**
	 * Post method to make existing user follow other user
	 * 
	 * @param followeeId
	 * @param followerId
	 */
	@PostMapping(path = "/follow/{followeeId}/{followerId}",consumes = "application/json")
	public void follow(@PathVariable Integer followeeId, @PathVariable Integer followerId) throws Exception {
		User followee = checkForUser(followeeId);
		User follower = checkForUser(followerId);
		this.socialMediaService.follow(followee, follower);
	}

	/**
	 * Post method to make existing user unfollow other user
	 * 
	 * @param followeeId
	 * @param followerId
	 */
	@PostMapping(path = "/unfollow/{followeeId}/{unfollowerId}",consumes = "application/json")
	public void unfollow(@PathVariable Integer followeeId, @PathVariable Integer unfollowerId) throws Exception {
		User followee = checkForUser(followeeId);
		User unfollower = checkForUser(unfollowerId);
		this.socialMediaService.unfollow(followee, unfollower);
	}

	/**
	 * get method to show follower of an existing user
	 * 
	 * @param followeeId
	 * @param followerId
	 */
	@GetMapping("/getfollower/{followeeId}")
	public String getfollower(@PathVariable Integer followeeId) throws Exception {
		User user = checkForUser(followeeId);
		StringBuilder userResponse = new StringBuilder("[");
		for (User follower : user.getFollowers()) {
			userResponse.append("{\"followerId\":" + follower.getUserId() + "\",");
			userResponse.append("\"followerName\":" + follower.getName() + "\"},\n");
		}
		userResponse.append("]");
		return userResponse.toString();
	}

	/**
	 * Get method to get related User posts only for given particular user
	 * 
	 * @param userId
	 */
	@GetMapping("/getUserNewFeed/{userId}")
	public String getUserNewsFeed(@PathVariable Integer userId) throws Exception {
		User user = checkForUser(userId);
		return this.socialMediaService.getUserNewsFeed(user);
	}
	/**
	 * Get method to get related home posts for given particular user
	 * 
	 * @param userId
	 */
	@GetMapping("/getHomeNewFeed/{userId}")
	public String getHomeNewsFeed(@PathVariable Integer userId) throws Exception {
		User user = checkForUser(userId);
		return this.socialMediaService.getHomeNewsFeed(user);
	}
	
	/**
	 * Get method to get allUser
	 * 
	 */
	@GetMapping("/getAllUser")
	public String getAllUser() throws Exception {
		StringBuilder userResponse = new StringBuilder("[");
		for (User user : this.socialMediaService.getAllUsers()) {
			userResponse.append("{\"userId\":\"" + user.getUserId() + "\",");
			userResponse.append("\"Name\":\"" + user.getName() + "\",");
			userResponse.append("\"Email\":\"" + user.getEmail() + "\",");
			userResponse.append("\"Posts\":[");
			for (Post post : user.getPosts()) {
				userResponse.append("{\"content\":\"" + post.getContent() + "\",");
				userResponse.append("\"date\":\"" + post.getDateTime() + "\"},");
			}
			userResponse.append("],\"Followers\":[");
			for (User follower : user.getFollowers()) {
				userResponse.append("{\"followerId\":\"" + follower.getUserId() + "\",");
				userResponse.append("\"followerName\":\"" + follower.getName() + "\"},");
			}
			userResponse.append("]}\n");
		}
		userResponse.append("]");
		return userResponse.toString();
	}

	/**
	 * Get method to get post
	 * 
	 */
	@GetMapping("/getAllPost")
	public String getAllPost() throws Exception {
		StringBuilder postResponse = new StringBuilder("[");
		for (Post post : this.socialMediaService.getAllPost()) {
			postResponse.append("{\"postId\":\"" + post.getPostId() + "\",");
			postResponse.append("\"content\":\"" + post.getContent() + "\",");
			postResponse.append("\"Date\":\"" + post.getDateTime() + "\",");
			postResponse.append("\"UserId\":\"" + post.getUser().getUserId() + "\",");
			postResponse.append("\"UserName\":\"" + post.getUser().getName() + "\"},");
		}
		postResponse.append("]");
		return postResponse.toString();
	}

	/**
	 * Private method to check existing user
	 * 
	 * @param userId
	 */
	private User checkForUser(Integer userId) throws Exception {
		User u = this.socialMediaService.getUserByUserID(userId);
		return u;
	}
}
