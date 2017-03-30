package com.dataagg.account;

import static org.springframework.util.Assert.isNull;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dataagg.account.service.AccountService;
import com.dataagg.commons.domain.EUser;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AccountApplication.class)
public class AccountApplicationTests {

	@Autowired
	private AccountService accountService;
	@Autowired
	private AuthenticationManager authenticationManager;
	//
	//	@Autowired
	//	private WeChatServiceClient weChatServiceClient;

	@Test
	public void testCreateAccount() {
		EUser user = new EUser();
		user.setUsername("name" + System.currentTimeMillis());
		user.setPassword("password");
		accountService.create(user);

		notNull(user, "");
		notNull(user.getUsername(), "");
	}

	@Test
	public void testWeChatService() {
		//		String redirect_uri = "";
	}

	@Test
	public void testSecurity() {
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication authentication = sc.getAuthentication();

		isNull(authentication, "未认证");

		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken("watano", "123456");
		// Perform the security
		authentication = authenticationManager.authenticate(upToken);
		sc.setAuthentication(authentication);

		authentication = sc.getAuthentication();

		notNull(authentication, "已认证");
		isTrue(authentication.isAuthenticated(), "未认证");

		//1.访问/user页面, 失败返回失败,提示登录

		//2.使用username和password提交到/login, 返回登录成功

		//3.附带token信息,再次访问/user页面, 成功返回

	}
}
