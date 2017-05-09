package com.dataagg.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.dataagg.APIGatewayApplication;
import com.dataagg.commons.dao.AuthorityDao;
import com.dataagg.commons.dao.UserDao;
import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EAuthority;
import com.dataagg.commons.domain.EUser;
import com.dataagg.commons.service.SysUserDetailsService;
import com.dataagg.security.UserPrincipal;

import static org.springframework.util.Assert.isNull;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import jodd.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = APIGatewayApplication.class)
public class SysUserDetailsServiceTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private SysUserDetailsService userDetailsService;

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void shouldCreateUser() {
		Assert.isTrue(userDao.exists(1L), "");
		EUser user = new EUser();
		user.setUsername("name" + System.currentTimeMillis());
		user.setPassword("password");
		user.setOrgId(1L);
		EAccount account = new EAccount();
		account.setFullName("test");
		account.setOrgId(1L);
		user.setAccount(account);
		userDetailsService.create(user);
		Assert.notNull(user, "");
		Assert.notNull(user.getId(), "");

		String username = user.getUsername();

		user = userDao.fetch(user.getId());
		Assert.notNull(user, "");
		Assert.notNull(user.getId(), "");
		Assert.isTrue(username.equals(user.getUsername()), "");
	}

	@Test
	public void testLoadUserByUsername() {
		//test found user
		String username = "watano";
		UserDetails ud = userDetailsService.loadUserByUsername(username);
		notNull(ud, "UserDetails is not null");
		notNull(ud.getUsername(), "Username is not null");
		isTrue(username.equals(ud.getUsername()), "Username is not " + username);
		notNull(ud.getPassword(), "Password is not null");

		//UsernameNotFoundException
		try {
			username = "watano1";
			ud = userDetailsService.loadUserByUsername(username);
			fail("UserDetails is not null");
		} catch (UsernameNotFoundException e) {}
	}

	@Test
	public void testFetchByName() {
		String username = "watano";
		EUser ud = userDetailsService.fetchByName(username);
		notNull(ud, "UserDetails is not null");
		notNull(ud.getUsername(), "Username is not null");
		isTrue(username.equals(ud.getUsername()), "Username is not " + username);
		notNull(ud.getPassword(), "Password is not null");
		username = "watano1";
		ud = userDetailsService.fetchByName(username);
		isNull(ud, "UserDetails is null");
	}

	@Test
	public void testUpdateAuthorities() {}

	@Test
	public void testFetchFullByName() {}

	@Test
	public void testFetchUserAuthoritiesPrincipal() {
		//principal==null时获取到guest权限信息
		Set<String> authorities = userDetailsService.principalAuthorities(null);
		notNull(authorities, "");
		isTrue(authorities.size() == 1, "");
		isTrue("guest".equals(StringUtil.join(authorities, ",")), "");
		//admin user时获取全部权限
		UserPrincipal up = new UserPrincipal("watano");
		authorities = userDetailsService.principalAuthorities(up);
		notNull(authorities, "");
		isTrue(authorities.size() > 0, "");
		isTrue(authorities.contains("pro_list"), "");
		List<EAuthority> allAuthorities = authorityDao.query();
		assertEquals(authorities.size(), allAuthorities.size());
	}

	@Test
	public void testFetchUserAuthoritiesString() {}

}
