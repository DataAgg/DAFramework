package com.dataagg.commons.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdNameEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EMenu;
import com.dataagg.util.ITreeNode;
import com.dataagg.util.SecurityHelper;

/**
 * Created by watano on 2017/2/26.
 */
@Component
public class MenuDao extends IdNameEntityService<EMenu> {
	@Autowired
	public MenuDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EMenu allMenus(Set<String> authorities) {
		List<ITreeNode<EMenu>> allNodes = getAllMenus(authorities);
		EMenu root = (EMenu) allNodes.get(0);
		return (EMenu) root.buildTree(allNodes);
	}

	public List<ITreeNode<EMenu>> getAllMenus(Set<String> authorities) {
		List<EMenu> allMenus = query(Cnd.where("flag", "=", "1").and("del_flag", "=", "0").orderBy("sort", "asc"));
		List<ITreeNode<EMenu>> allNodes = new ArrayList<>();
		EMenu root = new EMenu();
		root.setId(0L);
		allNodes.add(root);
		if (allMenus != null) {
			for (EMenu m : allMenus) {
				if (SecurityHelper.hasAuthority(authorities, m.getAuauthorities())) {
					allNodes.add(m);
				}
			}
		}
		return allNodes;
	}

	public EMenu buildMenuTree(List<EMenu> allMenus) {
		List<ITreeNode<EMenu>> allNodes = new ArrayList<>();
		EMenu root = new EMenu();
		root.setId(0L);
		allNodes.add(root);
		if (allMenus != null && !allMenus.isEmpty()) {
			allNodes.addAll(allMenus);
		}
		return (EMenu) root.buildTree(allNodes);
	}
}
