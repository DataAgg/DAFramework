package com.dataagg.security.dao;

import com.dataagg.commons.domain.EUser;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdNameEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by watano on 2017/2/26.
 */
@Component
public class UserDao extends IdNameEntityService<EUser> {
	private final Logger log = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	public UserDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
}
