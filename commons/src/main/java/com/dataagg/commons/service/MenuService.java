package com.dataagg.commons.service;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataagg.commons.dao.MenuDao;
import com.dataagg.commons.domain.EMenu;
import com.dataagg.util.Constans;

/**
 * 区域管理service实现类
 * Created by carlos on 2017/3/29.
 */
@Service
public class MenuService {
	@Autowired
	private MenuDao menuDao;

	public EMenu insert(EMenu menu) {
		menu.setDelFlag(Constans.POS_NEG.NEG);
		return menuDao._insert(menu);
	}

	public EMenu update(EMenu menu) {
		menu.setDelFlag(Constans.POS_NEG.NEG);
		return menuDao._update(menu) > 0 ? menu : null;
	}

	public boolean delete(long id) {
		EMenu menu = menuDao.fetch(id);
		if (menu == null) { return false; }
		Criteria cnd = Cnd.cri();
		cnd.where().andEquals("id", menu.getId());
		cnd.where().orLike("parent_ids", "%," + menu.getId() + ",%");

		Chain chain = Chain.make("del_flag", Constans.POS_NEG.POS);

		return menuDao.update(chain, cnd) > 0 ? true : false;
	}

	/**
	 * 通用的新增和修改方法
	 * @param menu
	 * @return
	 */
	public EMenu save(EMenu menu) {
		if (menu.getParentId() != null && menu.getParentId() != 0) {
			EMenu parent = menuDao.fetch(menu.getParentId());
			menu.setParentIds(parent.getParentIds() + parent.getId() + ",");
		}
		if (menu.getId() != null && menu.getId() > 0) {
			return update(menu);
		} else {
			return insert(menu);
		}
	}

}
