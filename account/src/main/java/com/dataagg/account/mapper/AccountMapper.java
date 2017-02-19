package com.dataagg.account.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dataagg.account.domain.Account;

import java.util.List;

/**
 * Created by watano on 2017/2/9.
 */
public interface AccountMapper extends BaseMapper<Account> {
	List<Account> getAll();
	Account getOne(Long id);
	Account findByName(String fullName);
}
