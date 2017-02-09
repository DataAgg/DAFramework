package com.dataagg.account.repository;

import com.dataagg.account.domain.Account;

import java.util.List;

/**
 * Created by watano on 2017/2/9.
 */
public interface AccountMapper {
	List<Account> getAll();
	Account getOne(Long id);
	Account findByName(String username);
	void insert(Account user);
	void save(Account user);
	void delete(Long id);
}
