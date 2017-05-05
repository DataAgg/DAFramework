package com.dataagg.commons.dao;

import java.util.List;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EDict;

/**
 * 数据字典管理DAO Created by carlos on 2017/3/29.
 */
@Component
public class DictDao extends IdEntityService<EDict> {

	@Autowired
	public DictDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
	public EDict fecthByTypeAndValue(String type, String value) {
		return dao().fetch(EDict.class, Cnd.where("type", "=", type).and("value","=",value));
	}
	public String fecthLableByTypeAndValue(String type, String value) {
		EDict dict = this.fecthByTypeAndValue(type, value);
		if(dict != null){
			return dict.getLabel();
		}
		return "";
	}
	/**
	 * 获取具体类型数据字典列表
	 * @param type
	 * @return
	 */
	public List<EDict> getDictListByType(String type){
		SimpleCriteria simpleCr=Cnd.cri();
		simpleCr.where().and("type", "=", type);
		simpleCr.asc("sort");
		return query(simpleCr);
		
	}
}
