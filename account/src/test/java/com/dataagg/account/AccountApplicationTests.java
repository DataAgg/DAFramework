package com.dataagg.account;

import com.dataagg.account.client.WeChatServiceClient;
import com.dataagg.account.service.AccountService;
import com.dataagg.commons.domain.EUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountApplication.class)
public class AccountApplicationTests {

	@Autowired
	private AccountService accountService;

	@Autowired
	private WeChatServiceClient weChatServiceClient;

	@Test
	public void testCreateAccount() {
		EUser EUser = new EUser();
		EUser.setUsername("name" + System.currentTimeMillis());
		EUser.setPassword("password");
		accountService.create(EUser);

		Assert.notNull(EUser);
		Assert.notNull(EUser.getUsername());
	}

	@Test
	public void testWeChatService() {
		String redirect_uri = "";
	}
}
