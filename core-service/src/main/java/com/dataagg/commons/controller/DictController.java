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

import com.dataagg.commons.domain.EDict;
import com.dataagg.commons.vo.ActionResultObj;
import com.dataagg.util.Constans;
import com.dataagg.util.SearchQueryJS;
import com.dataagg.util.WMap;
import com.dataagg.commons.dao.DictDao;
import com.dataagg.commons.service.DictService;

import jodd.util.StringUtil;


/**
 * Created by carlos on 2017/3/29.
 * 数据字典管理 controller
 */
@RestController
@RequestMapping("/dict")
public class DictController {
	private Logger LOG = LoggerFactory.getLogger(DictController.class);
	@Autowired
	public DictService dictService;
	@Autowired
	public DictDao dictDao;
	
	@RequestMapping(value="list")
	public ActionResultObj list(@RequestBody SearchQueryJS queryJs){
		ActionResultObj result = new ActionResultObj();
		try{
			Pager pager = queryJs.toPager();
			WMap query = queryJs.getQuery();
			
			//处理查询条件
			Criteria cnd = Cnd.cri();
			if(query.get("type") != null && StringUtil.isNotBlank(query.get("type").toString())){
				cnd.where().andEquals("type", query.get("type").toString());
			}
			cnd.getGroupBy().groupBy("type");
			cnd.getOrderBy().desc("sort");
			//分页查询
			List<EDict> projectList = dictDao.query(cnd, pager);
			
			//处理返回值
			WMap map = new WMap();
			if(queryJs.getQuery() != null){
				map.putAll(queryJs.getQuery());
			}
			map.put("data", projectList);
			map.put("page", queryJs.toWPage(pager));
			result.ok(map);
			result.okMsg("查询成功！");
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	@RequestMapping(value="type/{type}")
	public ActionResultObj findByType(@PathVariable String type){
		ActionResultObj result = new ActionResultObj();
		try{
			if(StringUtil.isNotBlank(type)){
					List<EDict> dictList = dictDao.query(Cnd.where("type", "=", type).and("del_flag", "=", Constans.POS_NEG.NEG).orderBy("sort", "asc"));
					//处理返回值
					WMap map = new WMap();
					map.put("type", type);
					map.put("data", dictList);
					result.ok(map);
					result.okMsg("查询成功！");
			}else{
				result.errorMsg("查询失败，字典类型不存在！");
			}
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("查询失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	@RequestMapping(value="/{id}")
	public ActionResultObj get(@PathVariable Long id){
		ActionResultObj result = new ActionResultObj();
		try{
			if(id != 0){
				EDict dict = dictDao.fetch(id);
				if(dict != null){
					result.ok(dict);
					result.okMsg("查询成功！");
				}else{
					result.errorMsg("查询失败！");
				}
			}else{
				result.errorMsg("查询失败，链接不存在！");
			}
		}catch(Exception e){
			LOG.error("查询失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	@RequestMapping(value="save")
	public ActionResultObj save(@RequestBody EDict dict){
		ActionResultObj result = new ActionResultObj();
		try{
			if(dictService.save(dict) != null){
				result.okMsg("保存成功！");
			}else{
				result.errorMsg("保存失败！");
			}
		}catch(Exception e){
			LOG.error("保存失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	
	@RequestMapping(value="delete")
	public ActionResultObj delete(@RequestBody Long id){
		ActionResultObj result = new ActionResultObj();
		try{
			if(id != 0){
				if(dictService.delete(id)){
					result.okMsg("删除成功！");
				}else{
					result.errorMsg("删除失败！");
				}
			}else{
				result.errorMsg("删除失败，链接不存在！");
			}
		}catch(Exception e){
			LOG.error("删除失败，原因："+e.getMessage());
			result.error(e);
		}
		return result;
	}
	
}
