package com.dataagg.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dataagg.commons.domain.EAuthority;
import com.dataagg.commons.domain.ERole;
import com.dataagg.commons.domain.EUser;
import com.dataagg.security.dao.RoleDao;
import com.dataagg.security.dao.UserDao;

/**
 * Created by samchu on 2017/2/15.
 */
@Service
public class SysUserDetailsService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(SysUserDetailsService.class);
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		log.debug("loadUserByUsername:{}", userName);
		return fetchByName(userName);
	}

	public EUser fetchByName(String userName) throws UsernameNotFoundException {
		EUser user = userDao.fetch(userName);
		if (user == null) { throw new UsernameNotFoundException(userName); }
		return user;
	}

	/**
	 * 更新user里的roles和authorities
	 * @param user
	 */
	public void updateAuthorities(EUser user) {
		userDao._fetchLinks(user, "roles");
		List<EAuthority> authorities = new ArrayList<>();
		if (user.getRoles() != null) {
			for (ERole role : user.getRoles()) {
				roleDao._fetchLinks(role, "authorities");
				if (role.getAuthorities() != null) {
					authorities.addAll(role.getAuthorities());
				}
			}
		}
		user.setGrantedAuthorities(authorities);
	}

	public EUser fetchFullByName(String userName) throws UsernameNotFoundException {
		EUser userDetails = fetchByName(userName);
		updateAuthorities(userDetails);
		return userDetails;
	}
}
