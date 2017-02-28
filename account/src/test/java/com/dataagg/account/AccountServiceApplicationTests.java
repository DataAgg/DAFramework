package com.dataagg.account;

import com.dataagg.account.domain.User;
import com.dataagg.account.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountApplication.class)
@WebAppConfiguration
public class AccountServiceApplicationTests {

	@Autowired
	private AccountService accountService;

	@Test
	public void testCreateAccount() {
		User user = new User();
		user.setUsername("name" + System.currentTimeMillis());
		user.setPassword("password");
		accountService.create(user);

		Assert.notNull(user);
		Assert.notNull(user.getUsername());
	}
}
