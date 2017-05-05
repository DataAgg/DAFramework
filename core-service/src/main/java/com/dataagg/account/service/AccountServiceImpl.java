package com.dataagg.account.service;

import org.nutz.dao.Cnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.dataagg.account.client.SecurityServiceClient;
import com.dataagg.commons.dao.AccountDao;
import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EUser;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityServiceClient authClient;

	@Autowired
	private AccountDao accountDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EAccount findByName(String accountName) {
		Assert.hasLength(accountName, "");
		return accountDao.fetch(Cnd.where("full_name", "=", accountName));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EAccount create(EUser user) {

		EAccount existing = findByName(user.getUsername());
		Assert.isNull(existing, "account already exists: " + user.getUsername());

		authClient.createUser(user);
		EAccount account = new EAccount();
		account.setFullName(user.getUsername());

		//accountMapper.insert(account);
		log.info("new account has been created: " + account.getFullName());

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAccount(String name, EAccount account) {
		EAccount account1 = findByName(name);
		Assert.notNull(account1, "can't find account1 with name " + name);

		accountDao._update(account1);

		log.debug("payment {} changes has been saved", name);
	}

}
