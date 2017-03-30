package com.dataagg.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dataagg.commons.domain.EAuthority;
import com.dataagg.commons.domain.ERole;
import com.dataagg.commons.domain.EUser;
import com.dataagg.security.dao.RoleDao;
import com.dataagg.security.dao.UserDao;

@Service
public class SysUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
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

	@Autowired
	private AuthenticationManager myAuthenticationManager = authentication -> {
		EUser userDetails = fetchByName(authentication.getName());
		updateAuthorities(userDetails);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	};

	public AuthenticationManager getAuthenticationManager() {
		return myAuthenticationManager;
	}
}
