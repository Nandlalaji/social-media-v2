package com.nand.sample.socialmedia.domain;

import java.util.List;

public class Followers {
	private List<FollowersDTO> followers;

	public List<FollowersDTO> getFollowers() {
		return followers;
	}

	public void setFollowers(List<FollowersDTO> followers) {
		this.followers = followers;
	}
}
