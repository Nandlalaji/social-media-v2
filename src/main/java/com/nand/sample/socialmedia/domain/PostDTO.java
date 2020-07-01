package com.nand.sample.socialmedia.domain;

import java.time.LocalDateTime;

public class PostDTO {

	private Integer postId;
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getUserUserId() {
		return userUserId;
	}
	public void setUserUserId(Integer userUserId) {
		this.userUserId = userUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String content;
	private LocalDateTime dateTime;
	private Integer userUserId;
	private String userName;
}
