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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dataagg.commons.dao.AreaDao;
import com.dataagg.commons.domain.EArea;
import com.dataagg.commons.service.AreaService;
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
@RequestMapping("/area")
public class AreaController {
	private Logger LOG = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	public AreaService areaService;
	@Autowired
	public AreaDao areaDao;

	@RequestMapping(value = "/list")
	public ActionResultObj list(@RequestBody SearchQueryJS queryJs) {
		ActionResultObj result = new ActionResultObj();
		try {
			Pager pager = queryJs.toPager();
			WMap query = queryJs.getQuery();

			//处理查询条件
			Criteria cnd = Cnd.cri();
			cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
			if (query.get("name") != null && StringUtil.isNotBlank(query.get("name").toString())) {
				cnd.where().andLike("name", query.get("name").toString()).orLike("full_name", query.get("name").toString());
			}
			cnd.getOrderBy().asc("sort");

			//分页查询
			List<EArea> areaList = areaDao.query(cnd, pager);
			pager.setRecordCount(areaDao.count(cnd));

			//处理返回值
			WMap map = new WMap();
			if (queryJs.getQuery() != null) {
				map.putAll(queryJs.getQuery());
			}
			map.put("data", areaList);
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

	@RequestMapping(value = "/allArea", method = RequestMethod.POST)
	public ActionResultObj findAllArea(@RequestBody SearchQueryJS queryJs) {
		ActionResultObj result = new ActionResultObj();
		try {
			EArea area = areaDao.allArea(queryJs.getQuery());
			WMap map = new WMap();
			map.put("data", area.getItems());
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
	public ActionResultObj save(@RequestBody EArea area) {
		ActionResultObj result = new ActionResultObj();
		try {
			if (areaService.save(area) != null) {
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
				EArea area = areaDao.fetch(id);
				if (area != null) {
					WMap map = new WMap();
					map.put("data", area);
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
				if (areaService.delete(id)) {
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

	@RequestMapping(value = "/kunMingArea")
	public ActionResultObj getKunMingArea() {
		ActionResultObj result = new ActionResultObj();
		try {
			WMap map = new WMap();
			map.put("data", areaDao.getAreaByParentId(1018L));
			result.setData(map);
			result.okMsg("查询成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询失败，原因：" + e.getMessage());
			result.error(e);
		}
		return result;
	}

}
