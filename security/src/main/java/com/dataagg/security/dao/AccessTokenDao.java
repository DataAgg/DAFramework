package com.dataagg.security.dao;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EAccessToken;

/**
 * Created by watano on 2017/3/20.
 */
@Component
public class AccessTokenDao extends IdEntityService<EAccessToken> {
	@Autowired
	public AccessTokenDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EAccessToken findByName(String name) {
		return fetch(Cnd.where("userName", "=", name));
	}
}
