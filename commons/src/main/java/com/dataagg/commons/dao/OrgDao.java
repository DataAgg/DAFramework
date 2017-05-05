package com.dataagg.commons.dao;

import com.dataagg.commons.domain.EOrg;

import javax.sql.DataSource;

import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 机构管理DAO
 * Created by carlos on 2017/3/29.
 */
@Component
public class OrgDao extends IdEntityService<EOrg> {

	@Autowired
	public OrgDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
	
}
