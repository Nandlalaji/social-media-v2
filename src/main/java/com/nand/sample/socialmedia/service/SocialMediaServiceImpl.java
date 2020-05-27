package com.nand.sample.socialmedia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nand.sample.socialmedia.model.Post;
import com.nand.sample.socialmedia.model.User;
import com.nand.sample.socialmedia.repository.PostRepository;
import com.nand.sample.socialmedia.repository.UserRepository;

/**
 * Implement class for PostService
 * 
 * @author Nand
 * @since 23th Sept 2019
 */
@Service
public class SocialMediaServiceImpl implements SocialMediaService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PostRepository postRepo;

	static TreeSet<User> userList = new TreeSet<>();
	static TreeSet<Post> posts = new TreeSet<>();
	static Map<Post, Integer> postUserMapping = new HashMap<>();

	@Override
	public void createPost(Integer userId, String content) throws Exception {
		Post post = new Post();
		post.setContent(content);
		post.setDateTime(LocalDateTime.now());
		post.setUser(userRepo.findById(userId).get());
		postRepo.save(post);
	}

	@Override
	public String getUserNewsFeed(User userTogetNew) throws Exception {

		StringBuilder contents = new StringBuilder("[") ;

		List<Post> posts = postRepo.findByUserOrderByDateTimeDesc(userTogetNew);
		int count = 0;
		for (Post post : posts) {

			contents.append("{\"content\":\"" + post.getContent() + "\",\"Date\":\"" + post.getDateTime() + "\"},");
			if (count++ >= 20) {
				break;
			}
		}
		contents.append("]");
		
		return contents.toString();
	}

	@Override
	public String getHomeNewsFeed(User userTogetNew) throws Exception {

		Map<LocalDateTime, String> storedContent =  
	               new TreeMap<LocalDateTime, String>(Collections.reverseOrder()); 

		List<Post> posts = postRepo.findByUserOrderByDateTimeDesc(userTogetNew);
		
		for (Post post : posts) {

			StringBuilder contents =new StringBuilder("{\"content\":\"" + post.getContent() 
			+ "\",\"Date\":\"" + post.getDateTime() 
			+ "\",\"Posted By\":\"" +post.getUser().getName()+"\"}");
			
			storedContent.put(post.getDateTime(), contents.toString());
		}
		for(User follower:userTogetNew.getFollowers()){
			List<Post> followerPosts = postRepo.findByUserOrderByDateTimeDesc(follower);
			for (Post post : followerPosts) {

				StringBuilder contents =new StringBuilder("{\"content\":\"" + post.getContent() 
				+ "\",\"Date\":\"" + post.getDateTime() 
				+ "\",\"Posted By\":\"" +post.getUser().getName()+"\"}");
				
				storedContent.put(post.getDateTime(), contents.toString());
			}
		}
		List<String> homeNewsContent = new ArrayList<>();
		Set<Entry<LocalDateTime, String>> set = storedContent.entrySet(); 
        Iterator<Entry<LocalDateTime, String>> itr = set.iterator(); 
  
        // Traverse map and print elements 
        while (itr.hasNext()) { 
            Map.Entry<LocalDateTime, String> me = (Map.Entry<LocalDateTime, String>)itr.next(); 
            homeNewsContent.add(me.getValue().toString()); 
        } 
		return homeNewsContent.toString();
	}

	@Override
	public Set<User> follow(User followee, User follower) throws Exception {

		Optional<User> user = userRepo.findById(followee.getUserId());
		Set<User> followers = user.get().getFollowers();
		followers.add(userRepo.findById(follower.getUserId()).get());
		user.get().setFollowers(followers);
		userRepo.save(user.get());

		return user.get().getFollowers();
	}

	@Override
	public Set<User> unfollow(User followee, User follower) throws Exception {
		Optional<User> user = userRepo.findById(followee.getUserId());
		Set<User> followers = user.get().getFollowers();
		if (followers.contains(follower)) {
			followers.remove(follower);
			user.get().setFollowers(followers);
			userRepo.save(user.get());
		}

		return user.get().getFollowers();
	}

	@Override
	public void setusers(User user) {
		userRepo.save(user);
	}

	@Override
	public Iterable<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public Iterable<Post> getAllPost() {
		return postRepo.findAll();
	}

	public User getUserByUserID(Integer userId) {
		return userRepo.findById(userId).get();
	}

}
