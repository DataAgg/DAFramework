package com.dataagg.account.service;

import com.dataagg.account.domain.Account;
import com.dataagg.account.domain.User;

public interface AccountService {

	/**
	 * Finds account by given name
	 *
	 * @param accountName
	 * @return found account
	 */
	Account findByName(String accountName);

	/**
	 * Checks if account with the same name already exists
	 * Invokes Auth Service user creation
	 * Creates new account with default parameters
	 *
	 * @param user
	 * @return created account
	 */
	Account create(User user);

	/**
	 * save account info
	 * @param name
	 * @param account
	 */
	void saveAccount(String name, Account account);
}
