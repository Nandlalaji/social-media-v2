package com.nand.sample.socialmedia.domain;

import java.util.List;

public class Users {
	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	private List<UserDTO> users;

}
