package com.dataagg.account.dao;

import javax.sql.DataSource;

import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dataagg.commons.domain.EAccount;

/**
 * Created by watano on 2017/3/1.
 */
@Component
public class AccountDao extends IdEntityService<EAccount> {
	@Autowired
	public AccountDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}
}
