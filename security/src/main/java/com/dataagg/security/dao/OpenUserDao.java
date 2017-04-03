package com.dataagg.security.dao;

import java.util.List;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EOpenUser;

/**
 * Created by watano on 2017/3/21.
 */
@Component
public class OpenUserDao extends IdEntityService<EOpenUser> {

	@Autowired
	public OpenUserDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EOpenUser createOrFind(EOpenUser openUser) {
		EOpenUser newEOpenUser = fetch(Cnd.where("clientId", "=", openUser.getClientId()).and("openId", "=", openUser.getOpenId()));
		if (newEOpenUser == null) {
			newEOpenUser = _insert(openUser);
		}
		return newEOpenUser;
	}

	public List<EOpenUser> findByName(String userName) {
		return query(Cnd.where("clientName", "=", userName));
	}
}
