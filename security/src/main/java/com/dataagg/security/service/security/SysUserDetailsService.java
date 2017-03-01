package com.dataagg.security.service.security;

import com.dataagg.commons.domain.EUser;
import com.dataagg.security.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SysUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EUser user = userDao.fetch(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
}
