package com.dataagg.commons.service;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dataagg.commons.domain.EArea;
import com.dataagg.util.Constans;
import com.dataagg.commons.dao.AreaDao;
import com.dataagg.commons.service.AreaService;

/**
 * 区域管理service实现类
 * Created by carlos on 2017/3/29.
 */
@Service
public class AreaService{
	@Autowired
	private AreaDao areaDao;
	
	public EArea insert(EArea area){
		area.setDelFlag(Constans.POS_NEG.NEG);
		return areaDao._insert(area);
	}
	public EArea update(EArea area){
		area.setDelFlag(Constans.POS_NEG.NEG);
		return areaDao._update(area) > 0 ? area : null;
	}
	public boolean delete(long id) {
		EArea area = areaDao.fetch(id);
		if(area ==null){
			return false;
		}
		Criteria cnd = Cnd.cri();
		cnd.where().andEquals("id", area.getId());
		cnd.where().orLike("parent_ids", "%,"+area.getId()+",%");
		
		Chain chain = Chain.make("del_flag", Constans.POS_NEG.POS);
		
		return areaDao.update(chain, cnd)>0 ? true : false;
	}
	
	/**
	 * 通用的新增和修改方法
	 * @param area
	 * @return
	 */
	public EArea save(EArea area){
		if(area.getParentId() != null && area.getParentId() != 0){
			EArea parent = areaDao.fetch(area.getParentId());
			area.setParentIds(parent.getParentIds()+parent.getId()+",");
			area.setFullName(parent.getFullName()+"-"+area.getName());
		}
		if (area.getId() != null && area.getId()>0) {
			return update(area);
		}else{
			return insert(area);
		}
	}
	
}
