package com.dataagg.account.service;

import com.dataagg.account.client.SecurityServiceClient;
import com.dataagg.account.domain.Account;
import com.dataagg.account.domain.User;
import com.dataagg.account.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SecurityServiceClient authClient;

	@Autowired
	private AccountMapper accountMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByName(String accountName) {
		Assert.hasLength(accountName);
		return accountMapper.findByName(accountName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account create(User user) {

		Account existing = accountMapper.findByName(user.getUsername());
		Assert.isNull(existing, "account already exists: " + user.getUsername());

		authClient.createUser(user);
		Account account = new Account();
		account.setFullName(user.getUsername());

		//accountMapper.insert(account);
		log.info("new account has been created: " + account.getFullName());

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveAccount(String name, Account update) {
		Account account = accountMapper.findByName(name);
		Assert.notNull(account, "can't find Account with name " + name);

		accountMapper.update(account, null);

		log.debug("payment {} changes has been saved", name);
	}

}
