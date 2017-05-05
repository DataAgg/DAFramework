package com.dataagg.commons.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Criteria;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EArea;
import com.dataagg.util.Constans;
import com.dataagg.util.ITreeNode;
import com.dataagg.util.WMap;

import jodd.util.StringUtil;

/**
 * 区域管理DAO
 * Created by carlos on 2017/3/29.
 */
@Component
public class AreaDao extends IdEntityService<EArea> {

	@Autowired
	public AreaDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EArea allArea(WMap query) {
		//处理查询条件
		Criteria cnd = Cnd.cri();
		cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
		if (query.get("extId") != null && StringUtil.isNotBlank(query.get("extId").toString())) {
			cnd.where().andNotIn("id", query.get("extId").toString());
			cnd.where().andNotLike("parent_ids", "%," + query.get("extId").toString() + ",%");
		}
		cnd.getOrderBy().asc("sort");
		List<EArea> allAreas = query(cnd);
		List<ITreeNode<EArea>> allNodes = new ArrayList<>();
		EArea root = new EArea();
		root.setId(0L);
		allNodes.add(root);
		if (allAreas != null && !allAreas.isEmpty()) {
			allNodes.addAll(allAreas);
		}
		return (EArea) root.buildTree(allNodes);
	}
	
	/**
	 * 根据一个parentId,得到其下级的地区
	 * @param parentId
	 * @return
	 */
	public List<EArea> getAreaByParentId(Long parentId){
		//处理查询条件
		Criteria cnd = Cnd.cri();
		cnd.where().andEquals("del_flag", Constans.POS_NEG.NEG);
		cnd.where().andEquals("parent_id", parentId);
		cnd.getOrderBy().asc("sort");
		List<EArea> areas = query(cnd);
		return areas;
	}
}
