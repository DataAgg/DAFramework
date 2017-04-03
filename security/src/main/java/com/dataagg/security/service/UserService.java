package com.dataagg.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dataagg.commons.domain.EUser;
import com.dataagg.security.dao.UserDao;

@Service
public class UserService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder encoder;

	public void create(EUser user) {
		EUser existing = userDao.fetch(user.getUsername());
		Assert.isNull(existing, "user already exists: " + user.getUsername());
		String hash = encoder.encode(user.getPassword());
		user.setPassword(hash);
		userDao._insert(user);
		log.info("new user has been created: {}", user.getUsername());
	}
}
