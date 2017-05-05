package com.dataagg.commons.controller;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dataagg.commons.dao.MenuDao;
import com.dataagg.commons.domain.EMenu;
import com.dataagg.commons.service.MenuService;
import com.dataagg.commons.vo.ActionResultObj;
import com.dataagg.util.Constans;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.WMap;

import jodd.util.StringUtil;

/**
 * Created by carlos on 2017/3/29.
 * 区域管理 controller
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
	private Logger LOG = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	public MenuService menuService;
	@Autowired
	public MenuDao menuDao;

	@RequestMapping(value = "/list")
	public ActionResultObj list(@RequestBody SearchQueryJS queryJs) {
		ActionResultObj result = new ActionResultObj();
		try {

			//处理查询条件
			Criteria cnd = Cnd.cri();
			cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
			cnd.getOrderBy().asc("sort");

			List<EMenu> allMenus = menuDao.query(cnd);
			EMenu menu = menuDao.buildMenuTree(allMenus);

			//处理返回值
			WMap map = new WMap();
			map.put("data", menu.getItems());
			result.ok(map);
			result.okMsg("查询成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	@RequestMapping(value = "/allMenu", method = RequestMethod.POST)
	public ActionResultObj findAllmenu(@RequestBody SearchQueryJS queryJs) {
		ActionResultObj result = new ActionResultObj();
		try {
			WMap query = queryJs.getQuery();
			//处理查询条件
			Criteria cnd = Cnd.cri();
			cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
			if (query.get("extId") != null && StringUtil.isNotBlank(query.get("extId").toString())) {
				cnd.where().andNotIn("id", query.get("extId").toString());
				cnd.where().andNotLike("parent_ids", "%," + query.get("extId").toString() + ",%");
			}
			cnd.getOrderBy().asc("sort");
			List<EMenu> allMenus = menuDao.query(cnd);
			EMenu menu = menuDao.buildMenuTree(allMenus);
			WMap map = new WMap();
			map.put("data", menu.getItems());
			result.setData(map);
			result.okMsg("查询成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	@RequestMapping(value = "/save")
	public ActionResultObj save(@RequestBody EMenu menu) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (menuService.save(menu) != null) {
				result.okMsg("保存成功！");
			} else {
				result.errorMsg("保存失败！");
			}
		} catch (Exception e) {
			LOG.error("保存失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	@RequestMapping(value = "/get/{id}")
	public ActionResultObj get(@PathVariable Long id) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (id > 0) {
				EMenu menu = menuDao.fetch(id);
				if (menu != null) {
					WMap map = new WMap();
					map.put("data", menu);
					result.ok(map);
					result.okMsg("查询成功！");
				} else {
					result.errorMsg("查询失败！");
				}
			} else {
				result.errorMsg("查询失败，链接不存在！");
			}
		} catch (Exception e) {
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	@RequestMapping(value = "/delete/{id}")
	public ActionResultObj delete(@PathVariable Long id) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (id != 0) {
				if (menuService.delete(id)) {
					result.okMsg("删除成功！");
				} else {
					result.errorMsg("删除失败！");
				}
			} else {
				result.errorMsg("删除失败，链接不存在！");
			}
		} catch (Exception e) {
			LOG.error("删除失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

}
