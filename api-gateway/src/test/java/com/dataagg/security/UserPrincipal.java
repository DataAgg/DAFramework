package com.dataagg.security;

import java.security.Principal;

public class UserPrincipal implements Principal {
	private String name;

	public UserPrincipal(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
