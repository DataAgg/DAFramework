package com.dataagg.security.service;

import com.dataagg.security.domain.User;
import com.dataagg.security.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserMapper userMapper;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void shouldCreateUser() {

		User user = new User();
		user.setUsername("name" + System.currentTimeMillis());
		user.setPassword("password");

		userService.create(user);
		//verify(userMapper, times(1)).insert(user);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenUserAlreadyExists() {

		User user = new User();
		user.setUsername("name" + System.currentTimeMillis());
		user.setPassword("password");

		when(userMapper.getByName(user.getUsername())).thenReturn(new User());
		userService.create(user);
	}
}
