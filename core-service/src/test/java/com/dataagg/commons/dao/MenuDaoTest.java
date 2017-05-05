package com.dataagg.commons.dao;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.dataagg.CoreServiceApplication;
import com.dataagg.commons.domain.EMenu;
import com.dataagg.commons.vo.ActionResultList;
import com.dataagg.util.ITreeNode;
import com.dataagg.util.WJsonUtils;

import jodd.io.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServiceApplication.class)
public class MenuDaoTest {
	private static final Logger LOG = LoggerFactory.getLogger(MenuDaoTest.class);

	//	@Autowired
	//	private MenuDao menuDao;
	//	@Autowired
	//	private SysUserDetailsService userDetailsService;

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	private void printMenuInsertSql(String id, String parentId, String name, String code, int menuType, String icon) {
		//System.out.println("#" + id + "--" + parentId + "--" + name + "--" + code + "--" + menuType + "--" + icon);
		System.out.println((code != null ? code : "") + name + "', '" + (code != null ? code : "") + "', " + menuType + ", '" + icon + "');");
	}

	//	@Test
	//	public void testAll() {
	//		//		EMenu root = menuDao.allMenus(new HashSet<String>());
	//		//		assertNotNull(root);
	//		//		assertNotNull(root.getItems());
	//		//		for (EMenu menu1 : root.getItems()) {
	//		//			System.out.println(menu1);
	//		//			if (menu1.getItems() != null) {
	//		//				for (EMenu menu2 : menu1.getItems()) {
	//		//					System.out.println(menu2);
	//		//					if (menu2.getItems() != null) {
	//		//						for (EMenu menu3 : menu2.getItems()) {
	//		//							System.out.println(menu3);
	//		//						}
	//		//					}
	//		//				}
	//		//			}
	//		//		}
	//		//check menus
	//		UserPrincipal up = new UserPrincipal("watano");
	//		Set<String> authorities = userDetailsService.principalAuthorities(up);
	//		List<ITreeNode<EMenu>> allNodes = menuDao.getAllMenus(authorities);
	//		assertEquals(32, allNodes.size());
	//		assertTrue(assertMenu(allNodes, 7010L));
	//
	//		up = new UserPrincipal("shilin001");
	//		authorities = userDetailsService.principalAuthorities(up);
	//		allNodes = menuDao.getAllMenus(authorities);
	//		assertEquals(7, allNodes.size());
	//		assertTrue(assertMenu(allNodes, 70L));
	//		assertTrue(assertMenu(allNodes, 7020L));
	//		assertFalse(assertMenu(allNodes, 7030L));
	//
	//	}

	public boolean assertMenu(List<ITreeNode<EMenu>> allNodes, long menuId) {
		for (ITreeNode<EMenu> node : allNodes) {
			if (node.getId().longValue() == menuId) { return true; }
		}
		return false;
	}

	@Test
	public void insertAll() {
		try {
			String json = FileUtil.readString("../../WdtcWeb/static/data/allMenus.json", "utf-8");
			ActionResultList<EMenu> allMenus = WJsonUtils.toObject(json, ActionResultList.class, EMenu.class);
			Assert.notNull(allMenus, "");
			Assert.notNull(allMenus.getData(), "");
			int index = 10;
			for (EMenu menu : allMenus.getData()) {
				if (menu.getItems() != null && menu.getItems().size() > 0) {
					printMenuInsertSql(index + "", "0", menu.getName(), menu.getCode(), 10, "message");
					int subIndex1 = 10;
					for (EMenu subMenu1 : menu.getItems()) {
						if (subMenu1.getItems() != null && subMenu1.getItems().size() > 0) {
							printMenuInsertSql(index + "" + subIndex1, index + "", subMenu1.getName(), subMenu1.getCode(), 20, "message");
							int subIndex2 = 10;
							for (EMenu subMenu2 : subMenu1.getItems()) {
								printMenuInsertSql(index + "" + subIndex1 + "" + subIndex2, index + "" + subIndex1, subMenu2.getName(), subMenu2.getCode(), 1, "message");
								subIndex2 += 10;
							}
						} else {
							printMenuInsertSql(index + "" + subIndex1, index + "", subMenu1.getName(), subMenu1.getCode(), 20, "message");
						}
						subIndex1 += 10;
					}
				} else {
					printMenuInsertSql(index + "", "0", menu.getName(), menu.getCode(), 1, "message");
				}
				index += 10;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
