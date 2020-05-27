package com.nand.sample.socialmedia.jwt.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;
	private String email;
	private String password;

	// need default constructor for JSON Parsing
	public JwtRequest() {
	}

	public JwtRequest(String email, String password) {
		this.setUsername(email);
		this.setPassword(password);
	}

	public String getEmail() {
		return this.email;
	}

	public void setUsername(String username) {
		this.email = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
