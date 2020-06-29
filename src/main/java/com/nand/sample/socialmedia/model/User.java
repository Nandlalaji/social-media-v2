package com.nand.sample.socialmedia.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * POJO class for object of User
 * 
 * @author Nand
 * @since 23th Sept 2019
 */
@Table(name = "users")
@Entity
public class User implements Comparable<User> {

	@Id
    @GeneratedValue
	Integer userId;

	String name;
	
	String password;

	String email;
	
	@OneToMany(targetEntity=Post.class, mappedBy="user",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private Set<Post> posts;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="followers",
		joinColumns={@JoinColumn(name="userId")},
		inverseJoinColumns={@JoinColumn(name="followerId")})
	private Set<User> followers;

	@ManyToMany(mappedBy="followers")
	private Set<User> mainUser = new HashSet<User>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = hashPassword(password);
	}
	
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getMainUser() {
		return mainUser;
	}

	public void setMainUser(Set<User> mainUser) {
		this.mainUser = mainUser;
	}

	private String hashPassword(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
   
	public boolean checkPassword(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public int compareTo(User arg0) {
		return this.getUserId() - arg0.getUserId();
	}

}
