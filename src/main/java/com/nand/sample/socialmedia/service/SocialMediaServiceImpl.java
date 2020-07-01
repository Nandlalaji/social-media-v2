package com.nand.sample.socialmedia.service;

import static java.util.stream.Collectors.toList;

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

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nand.sample.socialmedia.domain.Followers;
import com.nand.sample.socialmedia.domain.FollowersDTO;
import com.nand.sample.socialmedia.domain.PostDTO;
import com.nand.sample.socialmedia.domain.Posts;
import com.nand.sample.socialmedia.domain.UserDTO;
import com.nand.sample.socialmedia.domain.Users;
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

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional
	public void createPost(Integer userId, String content) throws SocialMediaDAOException, SocialMediaRuntimeException {
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
			System.err.println("DAO Exception in createPost method" + dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in createPost method" + ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	public Posts getUserNewsFeed(User userTogetNew) throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {
			Posts posts = new Posts();
			posts.setPosts(postRepo.findByUserOrderByDateTimeDesc(userTogetNew).stream()
					.map(userPost -> modelMapper.map(userPost, PostDTO.class)).collect(toList()));

			return posts;
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in getUserNewsFeed method" + dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in getUserNewsFeed method" + ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	public Posts getHomeNewsFeed(User userTogetNew) throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {

			Map<LocalDateTime, PostDTO> storedContent = new TreeMap<LocalDateTime, PostDTO>(Collections.reverseOrder());

			List<Post> posts = postRepo.findByUserOrderByDateTimeDesc(userTogetNew);

			for (Post post : posts) {
				storedContent.put(post.getDateTime(), modelMapper.map(post, PostDTO.class));
			}
			for (User follower : userTogetNew.getFollowers()) {
				List<Post> followerPosts = postRepo.findByUserOrderByDateTimeDesc(follower);
				for (Post post : followerPosts) {
					storedContent.put(post.getDateTime(), modelMapper.map(post, PostDTO.class));
				}
			}
			List<PostDTO> homeNewsContent = new ArrayList<>();
			Set<Entry<LocalDateTime, PostDTO>> set = storedContent.entrySet();
			Iterator<Entry<LocalDateTime, PostDTO>> itr = set.iterator();

			// Traverse map and print elements
			while (itr.hasNext()) {
				Map.Entry<LocalDateTime, PostDTO> me = (Map.Entry<LocalDateTime, PostDTO>) itr.next();
				homeNewsContent.add(me.getValue());
			}
			Posts homePosts = new Posts();
			homePosts.setPosts(homeNewsContent);

			return homePosts;
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in getHomeNewsFeed method" + dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in getHomeNewsFeed method" + ex.getMessage());
			throw new SocialMediaRuntimeException("Runtime exception occured");
		}
	}

	@Override
	@Transactional
	public Set<User> follow(User followee, User follower) throws SocialMediaDAOException, SocialMediaRuntimeException {
		try {
			Optional<User> user = userRepo.findById(followee.getUserId());
			Set<User> followers = user.get().getFollowers();
			followers.add(userRepo.findById(follower.getUserId()).get());
			user.get().setFollowers(followers);
			userRepo.save(user.get());

			return user.get().getFollowers();
		} catch (DataAccessException dae) {
			System.err.println("DAO Exception in follow method" + dae.getMessage());
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
			System.err.println("DAO Exception in unfollow method" + dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		} catch (Exception ex) {
			System.err.println("Exception in unfollow method" + ex.getMessage());
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
	public Users getAllUsers() throws SocialMediaDAOException {
		try {
			Users users = new Users();
			users.setUsers(
					userRepo.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList()));
			return users;
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("No User Avaiable in DB");
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Some DB related exception");
		}
	}

	@Override
	public Posts getAllPost() throws SocialMediaDAOException {
		try {
			Posts posts = new Posts();
			posts.setPosts(
					postRepo.findAll().stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(toList()));
			return posts;
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
			throw new SocialMediaDAOException("DB Operation Issue in finding User");
		}
	}

	@Override
	public void removeUser(Integer userId) throws SocialMediaDAOException {
		try {
			userRepo.deleteById(userId);
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Issue in deleting User with userId" + userId);
		}
	}

	@Override
	public void removePost(Integer postId) throws SocialMediaDAOException {
		try {
			postRepo.deleteById(postId);
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("Issue in deleting Post with postId" + postId);
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
			throw new SocialMediaDAOException("DB operation issue in finding Post");
		}
	}

	@Override
	public Followers getFollowers(Integer followeeId) throws SocialMediaDAOException{
		try {
			Followers followers = new Followers();
			List<FollowersDTO> followersDTO = new ArrayList<>();
			User followee = userRepo.findById(followeeId).get();
			for (User follower : followee.getFollowers()) {
				followersDTO.add(modelMapper.map(follower, FollowersDTO.class));
			}
			followers.setFollowers(followersDTO);
			return followers;
		} catch (NoSuchElementException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("User_NoT_Found with provided followee id");
		} catch (DataAccessException dae) {
			System.err.println(dae.getMessage());
			throw new SocialMediaDAOException("DB operation issue in finding user");
		}
	}
}
