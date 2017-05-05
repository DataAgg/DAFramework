package com.dataagg.commons.service;

import com.dataagg.commons.dao.OrgDao;
import com.dataagg.commons.domain.EOrg;
import com.dataagg.util.Constans;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构管理service实现类
 * Created by carlos on 2017/3/29.
 */
@Service
public class OrgService{
	@Autowired
	private OrgDao orgDao;
	
	public EOrg insert(EOrg org){
		org.setDelFlag(Constans.POS_NEG.NEG);
		return orgDao._insert(org);
	}
	public EOrg update(EOrg org){
		org.setDelFlag(Constans.POS_NEG.NEG);
		return orgDao._update(org) > 0 ? org : null;
	}
	public boolean delete(long id) {
		EOrg org = orgDao.fetch(id);
		if(org ==null){
			return false;
		}
		Criteria cnd = Cnd.cri();
		cnd.where().andEquals("id", org.getId());
		cnd.where().orLike("parent_ids", "%,"+org.getId()+",%");
		
		Chain chain = Chain.make("del_flag", Constans.POS_NEG.POS);
		
		return orgDao.update(chain, cnd)>0 ? true : false;
	}
	
	/**
	 * 通用的新增和修改方法
	 * @param org
	 * @return
	 */
	public EOrg save(EOrg org){
		if (org.getId() != null && org.getId()>0) {
			return update(org);
		}else{
			return insert(org);
		}
	}
	
}
