package com.nand.sample.socialmedia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nand.sample.socialmedia.exception.SocialMediaDAOException;
import com.nand.sample.socialmedia.exception.SocialMediaRuntimeException;
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

	@Override
	@Transactional
	public void createPost(Integer userId, String content)
			throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {
			Post post = new Post();
			post.setContent(content);
			post.setDateTime(LocalDateTime.now());
			post.setUser(userRepo.findById(userId).get());
			postRepo.save(post);
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("User_NoT_Found with provided userId");
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in createPost method"
					+ dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in createPost method"
					+ ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	public String getUserNewsFeed(User userTogetNew)
			throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {
			StringBuilder contents = new StringBuilder("[");

			List<Post> posts = postRepo
					.findByUserOrderByDateTimeDesc(userTogetNew);
			int count = 0;
			for (Post post : posts) {

				contents.append("{\"content\":\"" + post.getContent()
						+ "\",\"Date\":\"" + post.getDateTime() + "\"},");
				if (count++ >= 20) {
					break;
				}
			}
			contents.append("]");

			return contents.toString();
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in getUserNewsFeed method"
					+ dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in getUserNewsFeed method"
					+ ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	public String getHomeNewsFeed(User userTogetNew)
			throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {

			Map<LocalDateTime, String> storedContent = new TreeMap<LocalDateTime, String>(
					Collections.reverseOrder());

			List<Post> posts = postRepo
					.findByUserOrderByDateTimeDesc(userTogetNew);

			for (Post post : posts) {

				StringBuilder contents = new StringBuilder("{\"content\":\""
						+ post.getContent() + "\",\"Date\":\""
						+ post.getDateTime() + "\",\"Posted By\":\""
						+ post.getUser().getName() + "\"}");

				storedContent.put(post.getDateTime(), contents.toString());
			}
			for (User follower : userTogetNew.getFollowers()) {
				List<Post> followerPosts = postRepo
						.findByUserOrderByDateTimeDesc(follower);
				for (Post post : followerPosts) {

					StringBuilder contents = new StringBuilder(
							"{\"content\":\"" + post.getContent()
									+ "\",\"Date\":\"" + post.getDateTime()
									+ "\",\"Posted By\":\""
									+ post.getUser().getName() + "\"}");

					storedContent.put(post.getDateTime(), contents.toString());
				}
			}
			List<String> homeNewsContent = new ArrayList<>();
			Set<Entry<LocalDateTime, String>> set = storedContent.entrySet();
			Iterator<Entry<LocalDateTime, String>> itr = set.iterator();

			// Traverse map and print elements
			while (itr.hasNext()) {
				Map.Entry<LocalDateTime, String> me = (Map.Entry<LocalDateTime, String>) itr
						.next();
				homeNewsContent.add(me.getValue().toString());
			}
			return homeNewsContent.toString();
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in getHomeNewsFeed method"
					+ dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in getHomeNewsFeed method"
					+ ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	@Transactional
	public Set<User> follow(User followee, User follower)
			throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {
			Optional<User> user = userRepo.findById(followee.getUserId());
			Set<User> followers = user.get().getFollowers();
			followers.add(userRepo.findById(follower.getUserId()).get());
			user.get().setFollowers(followers);
			userRepo.save(user.get());

			return user.get().getFollowers();
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in follow method"
					+ dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in follow method" + ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	@Transactional
	public Set<User> unfollow(User followee, User follower)
			throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {
			Optional<User> user = userRepo.findById(followee.getUserId());
			Set<User> followers = user.get().getFollowers();
			if (followers.contains(follower)) {
				followers.remove(follower);
				user.get().setFollowers(followers);
				userRepo.save(user.get());
			}

			return user.get().getFollowers();
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in unfollow method"
					+ dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err
					.println("Exception in unfollow method" + ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	public void setusers(User user) throws SocialMediaDAOException {
		try {
			userRepo.save(user);
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		}
	}

	@Override
	public Iterable<User> getAllUsers() throws SocialMediaDAOException {
		try {
			return userRepo.findAll();
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("No User Avaiable in DB");
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		}
	}

	@Override
	public Iterable<Post> getAllPost() throws SocialMediaDAOException {
		try {
			return postRepo.findAll();
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("No Post Available in DB");
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		}
	}

	public User getUserByUserID(Integer userId) throws SocialMediaDAOException {
		try {
			return userRepo.findById(userId).get();
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("User_NoT_Found with provided userId");
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException(
					"DB Operation Issue in finding User");
		}
	}

	@Override
	public void removeUser(Integer userId) throws SocialMediaDAOException {
		try {
			userRepo.deleteById(userId);
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException(
					"Issue in deleting User with userId" + userId);
		}
	}

	@Override
	public void removePost(Integer postId) throws SocialMediaDAOException {
		try {
			postRepo.deleteById(postId);
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException(
					"Issue in deleting Post with postId" + postId);
		}

	}

	@Override
	public Post getPostByPostID(Integer postId) throws SocialMediaDAOException {
		try {
			return postRepo.findById(postId).get();
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Post_NoT_Found with provided post id");
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException(
					"DB operation issue in finding Post");
		}
	}
}
