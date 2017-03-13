package com.dataagg.security;

import com.dataagg.commons.domain.EUser;
import com.dataagg.security.dao.UserDao;
import com.dataagg.security.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SecurityApplication.class)
public class SecurityApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@Test
	public void shouldCreateUser() {
		Assert.isTrue(userDao.exists("watano"), "");
		EUser user = new EUser();
		user.setUsername("name" + System.currentTimeMillis());
		user.setPassword("password");

		userService.create(user);
		Assert.notNull(user, "");
		Assert.notNull(user.getId(), "");

		String username = user.getUsername();

		user = userDao.fetch(user.getId());
		Assert.notNull(user, "");
		Assert.notNull(user.getId(), "");
		Assert.isTrue(username.equals(user.getUsername()), "");
	}

}
