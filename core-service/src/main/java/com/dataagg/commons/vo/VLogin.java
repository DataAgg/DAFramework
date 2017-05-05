package com.dataagg.commons.vo;

import java.io.Serializable;

public class VLogin implements Serializable {
	private static final long serialVersionUID = 5916516763070125465L;
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
