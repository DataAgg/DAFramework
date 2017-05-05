package com.dataagg.commons.controller;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dataagg.commons.domain.EArea;
import com.dataagg.commons.domain.EOrg;
import com.dataagg.commons.service.OrgService;
import com.dataagg.commons.vo.ActionResultObj;
import com.dataagg.util.Constans;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.WMap;
import com.dataagg.commons.dao.AreaDao;
import com.dataagg.commons.dao.OrgDao;

import jodd.util.StringUtil;

/**
 * Created by carlos on 2017/3/29. 机构管理 controller
 */
@RestController
@RequestMapping("/org")
public class OrgController {
	private Logger LOG = LoggerFactory.getLogger(OrgController.class);
	@Autowired
	public OrgService orgService;
	@Autowired
	public OrgDao orgDao;
	@Autowired
	public AreaDao areaDao;

	@RequestMapping(value = "/list")
	public ActionResultObj list(@RequestBody SearchQueryJS queryJs) {
		ActionResultObj result = new ActionResultObj();
		try {
			Pager pager = queryJs.toPager();
			WMap query = queryJs.getQuery();

			// 处理查询条件
			Criteria cnd = Cnd.cri();
			cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
			if (query.get("queryArea") != null && StringUtil.isNotBlank(query.get("queryArea").toString())) {
				cnd.where().andEquals("area_id", query.get("queryArea").toString());
			}
			if (query.get("queryType") != null && StringUtil.isNotBlank(query.get("queryType").toString())) {
				cnd.where().andEquals("type", query.get("queryType").toString());
			}
			if (query.get("queryName") != null && StringUtil.isNotBlank(query.get("queryName").toString())) {
				cnd.where().andLike("name", "%" + query.get("queryName").toString() + "%");
			}
			cnd.getOrderBy().asc("id");

			// 分页查询
			List<EOrg> orgList = orgDao.query(cnd, pager);
			pager.setRecordCount(orgDao.count(cnd));
			for (EOrg eOrg : orgList) {
				EArea area = areaDao.fetch(eOrg.getAreaId());
				if (area != null) {
					eOrg.setArea(area);
				}
			}

			// 处理返回值
			WMap map = new WMap();
			if (queryJs.getQuery() != null) {
				map.putAll(queryJs.getQuery());
			}
			map.put("data", orgList);
			map.put("page", queryJs.toWPage(pager));
			result.ok(map);
			result.okMsg("查询成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

	@RequestMapping(value = "/get/{id}")
	public ActionResultObj get(@PathVariable Long id) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (id != 0) {
				EOrg org = orgDao.fetch(id);
				if (org != null) {
					WMap map = new WMap();
					map.put("data", org);
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

	@RequestMapping(value = "/save")
	public ActionResultObj save(@RequestBody EOrg org) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (orgService.save(org) != null) {
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

	@RequestMapping(value = "/delete/{id}")
	public ActionResultObj delete(@PathVariable Long id) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (id != 0) {
				if (orgService.delete(id)) {
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

	@RequestMapping(value = "/getOrgs/{type}")
	public ActionResultObj getOrgsByListByType(@PathVariable String type) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (StringUtil.isNotBlank(type)) {
				// 处理查询条件
				Criteria cnd = Cnd.cri();
				if(!type.equals("all")){
					cnd.where().andEquals("type", type);
				}
				cnd.getOrderBy().desc("id");

				// 分页查询
				List<EOrg> orgList = orgDao.query(cnd);

				// 处理返回值
				WMap map = new WMap();
				map.put("data", orgList);
				result.ok(map);
				result.okMsg("查询成功！");
			} else {
				result.errorMsg("删除失败，链接不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}
}
