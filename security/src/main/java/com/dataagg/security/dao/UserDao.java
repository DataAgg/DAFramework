package com.dataagg.security.dao;

import com.dataagg.commons.domain.EUser;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdNameEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by watano on 2017/2/26.
 */
@Component
public class UserDao extends IdNameEntityService<EUser> {
	@Autowired
	public UserDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EUser fetchByName(String userName){
		return fetch(Cnd.where("userName", "=", userName));
	}
}
