package com.dataagg.paymentService.service;

import com.dataagg.paymentService.domain.Account;
import com.dataagg.paymentService.domain.User;

public interface AccountService {

	/**
	 * Finds paymentService by given name
	 *
	 * @param accountName
	 * @return found paymentService
	 */
	Account findByName(String accountName);

	/**
	 * Checks if paymentService with the same name already exists
	 * Invokes Auth Service user creation
	 * Creates new paymentService with default parameters
	 *
	 * @param user
	 * @return created paymentService
	 */
	Account create(User user);

	/**
	 * Validates and applies incoming paymentService updates
	 * Invokes Statistics Service update
	 *
	 * @param name
	 * @param update
	 */
	void saveChanges(String name, Account update);
}
