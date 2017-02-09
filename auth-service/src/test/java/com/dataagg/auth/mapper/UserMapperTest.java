package com.dataagg.auth.mapper;

import com.dataagg.auth.AuthApplication;
import com.dataagg.auth.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthApplication.class)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void shouldSaveAndFindUserByName() {

		User user = new User();
		user.setUsername("name"+ System.currentTimeMillis());
		user.setPassword("password");
		userMapper.insert(user);

		User found = userMapper.getByName(user.getUsername());
		assertEquals(user.getUsername(), found.getUsername());
		assertEquals(user.getPassword(), found.getPassword());
	}
}
