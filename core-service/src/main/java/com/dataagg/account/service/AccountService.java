package com.dataagg.account.service;

import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EUser;

public interface AccountService {

	/**
	 * Finds account by given name
	 *
	 * @param accountName
	 * @return found account
	 */
	EAccount findByName(String accountName);

	/**
	 * Checks if account with the same name already exists
	 * Invokes Auth Service user creation
	 * Creates new account with default parameters
	 *
	 * @param user
	 * @return created account
	 */
	EAccount create(EUser user);

	/**
	 * save EAccount info
	 * @param name
	 * @param account
	 */
	void saveAccount(String name, EAccount account);
}
