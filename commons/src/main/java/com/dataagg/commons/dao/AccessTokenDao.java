package com.dataagg.commons.dao;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EAccessToken;

/**
 * Created by watano on 2017/3/20.
 */
@Component
public class AccessTokenDao extends IdEntityService<EAccessToken> {
	private final Logger log = LoggerFactory.getLogger(AccessTokenDao.class);

	@Autowired
	public AccessTokenDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EAccessToken findByName(String name) {
		log.debug("findByName");
		return fetch(Cnd.where("userName", "=", name));
	}
}
