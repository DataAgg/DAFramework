package com.dataagg.security.dao;

import com.dataagg.commons.domain.*;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by watano on 2017/3/13.
 */
@Component
public class RoleDao extends IdEntityService<ERole> {
	private final Logger log = LoggerFactory.getLogger(RoleDao.class);

	@Autowired
	public RoleDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
}
