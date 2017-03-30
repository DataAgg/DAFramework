package com.dataagg.security.dao;

import javax.sql.DataSource;

import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.ERole;

/**
 * Created by watano on 2017/3/13.
 */
@Component
public class RoleDao extends IdEntityService<ERole> {
	@Autowired
	public RoleDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
}
