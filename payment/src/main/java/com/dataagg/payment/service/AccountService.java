package com.dataagg.payment.service;

import com.dataagg.payment.domain.Account;
import com.dataagg.payment.domain.User;

public interface AccountService {

	/**
	 * Finds payment by given name
	 *
	 * @param accountName
	 * @return found payment
	 */
	Account findByName(String accountName);

	/**
	 * Checks if payment with the same name already exists
	 * Invokes Auth Service user creation
	 * Creates new payment with default parameters
	 *
	 * @param user
	 * @return created payment
	 */
	Account create(User user);

	/**
	 * Validates and applies incoming payment updates
	 * Invokes Statistics Service update
	 *
	 * @param name
	 * @param update
	 */
	void saveChanges(String name, Account update);
}
