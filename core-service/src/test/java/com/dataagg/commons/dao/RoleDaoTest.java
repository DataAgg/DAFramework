package com.dataagg.commons.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dataagg.CoreServiceApplication;
import com.dataagg.commons.domain.ERole;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
public class RoleDaoTest {
	@Autowired
	private RoleDao roleDao;

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testGetDefaultRoles() {}

	@Test
	public void testFetchByName() {}

	@Test
	public void testFetchFullByName() {}

	@Test
	public void testSave() {
		ERole adminRole = new ERole();
		adminRole.setId(1L);
		adminRole.setName("Admin");
		adminRole.setDescription("系统管理员");
		adminRole = roleDao.save(adminRole);
		assertEquals(new Long(1L), adminRole.getId());
		adminRole = roleDao.fetch(1L);
		assertNotNull(adminRole);
		assertEquals(new Long(1L), adminRole.getId());
	}

	@Test
	public void testInsertAll() {}

	@Test
	public void testUpdateAuthorities() {}

}
