package com.dataagg.commons.dao;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EUser;

/**
 * Created by watano on 2017/2/26.
 */
@Component
public class UserDao extends IdEntityService<EUser> {
	@Autowired
	public UserDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EUser fetchByName(String name) {
		return fetch(Cnd.where("username", "=", name));
	}

	//FIXME pls polish this codes
	public EUser updateRoles(EUser vo) {
		dao().execute(Sqls.create("delete from sys_user_role where userid=" + vo.getId()));
		return _insertRelation(vo, "roles");
	}
}
