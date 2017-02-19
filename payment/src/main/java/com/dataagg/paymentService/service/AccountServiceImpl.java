package com.dataagg.paymentService.service;

import com.dataagg.paymentService.client.AuthServiceClient;
import com.dataagg.paymentService.domain.Account;
import com.dataagg.paymentService.domain.Currency;
import com.dataagg.paymentService.domain.Saving;
import com.dataagg.paymentService.domain.User;
import com.dataagg.paymentService.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthServiceClient authClient;

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
		Assert.isNull(existing, "paymentService already exists: " + user.getUsername());

		authClient.createUser(user);

		Saving saving = new Saving();
		saving.setAmount(new BigDecimal(0));
		saving.setCurrency(Currency.getDefault());
		saving.setInterest(new BigDecimal(0));
		saving.setDeposit(false);
		saving.setCapitalization(false);

		Account account = new Account();
		account.setName(user.getUsername());
		account.setLastSeen(new Date());
		account.setSaving(saving);

		//accountMapper.insert(paymentService);
		log.info("new paymentService has been created: " + account.getName());

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveChanges(String name, Account update) {

		Account account = accountMapper.findByName(name);
		Assert.notNull(account, "can't find paymentService with name " + name);

		account.setIncomes(update.getIncomes());
		account.setExpenses(update.getExpenses());
		account.setSaving(update.getSaving());
		account.setNote(update.getNote());
		account.setLastSeen(new Date());
		accountMapper.update(account, null);

		log.debug("paymentService {} changes has been saved", name);
	}
}
