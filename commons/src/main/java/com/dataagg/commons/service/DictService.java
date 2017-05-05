package com.dataagg.commons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataagg.commons.domain.EDict;
import com.dataagg.util.Constans;
import com.dataagg.commons.dao.DictDao;
import com.dataagg.commons.service.DictService;

/**
 * 数据字典管理service实现类
 * Created by carlos on 2017/3/29.
 */
@Service
public class DictService{
	@Autowired
	private DictDao dictDao;
	
	public EDict insert(EDict dict){
		dict.setDelFlag(Constans.POS_NEG.NEG);
		return dictDao._insert(dict);
	}
	public EDict update(EDict dict){
		dict.setDelFlag(Constans.POS_NEG.NEG);
		return dictDao._update(dict) > 0 ? dict : null;
	}
	public boolean delete(long id) {
		EDict dict = dictDao.fetch(id);
		if(dict ==null){
			return false;
		}
		dict.setDelFlag(Constans.POS_NEG.POS);
		return dictDao._update(dict)>0 ? true : false;
	}
	
	/**
	 * 通用的新增和修改方法
	 * @param dict
	 * @return
	 */
	public EDict save(EDict dict){
		if (dict.getId() != null && dict.getId()>0) {
			return update(dict);
		}else{
			return insert(dict);
		}
	}
	
}
