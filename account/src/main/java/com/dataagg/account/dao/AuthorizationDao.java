package com.dataagg.account.dao;

import com.dataagg.commons.domain.EAuthorization;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by watano on 2017/3/1.
 */
@Component
public class AuthorizationDao extends IdEntityService<EAuthorization> {
	private final Logger log = LoggerFactory.getLogger(AuthorizationDao.class);

	@Autowired
	public AuthorizationDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
}
