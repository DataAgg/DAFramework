package com.dataagg.security.dao;

import javax.sql.DataSource;

import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.service.IdEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EUser;

/**
 * Created by watano on 2017/3/21.
 */
@Component
public class AccountDao extends IdEntityService<EAccount> {
	@Autowired
	public AccountDao(@Autowired DataSource dataSource) {
		super(new NutDao(dataSource));
	}

	public EAccount fetch(EUser user) {
		return fetch(Cnd.where("userId", "=", user.getId()));
	}

	public EAccount create(EAccount account) {
		Assert.notNull(account, "account不能为null");
		Assert.notNull(account.getUser(), "account中的user不能为null");
		Assert.notNull(account.getUser().getId(), "account中的user.id不能为null");
		account.setUserId(account.getUser().getId());
		return _insert(account);
	}
}
