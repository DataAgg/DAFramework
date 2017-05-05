package com.dataagg.commons.dao;

import java.util.List;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EOpenUser;

/**
 * Created by watano on 2017/3/21.
 */
@Component
public class OpenUserDao extends IdEntityService<EOpenUser> {
	private final Logger log = LoggerFactory.getLogger(OpenUserDao.class);

	@Autowired
	public OpenUserDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EOpenUser createOrFind(EOpenUser openUser) {
		EOpenUser newEOpenUser = fetch(Cnd.where("clientId", "=", openUser.getClientId()).and("openId", "=", openUser.getOpenId()));
		if (newEOpenUser == null) {
			newEOpenUser = _insert(openUser);
		}
		log.debug("createOrFind");
		return newEOpenUser;
	}

	public List<EOpenUser> findByName(String userName) {
		return query(Cnd.where("clientName", "=", userName));
	}
}
