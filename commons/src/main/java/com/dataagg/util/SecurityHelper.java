package com.dataagg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dataagg.commons.domain.EAuthority;

public class SecurityHelper {
	public static Authentication getAuthentication() {
		SecurityContext sc = SecurityContextHolder.getContext();
		return sc.getAuthentication();
	}

	public static Authentication auth(AuthenticationManager authenticationManager, String userName, String password) {
		SecurityContext sc = SecurityContextHolder.getContext();
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
		Authentication authentication = authenticationManager.authenticate(upToken);
		sc.setAuthentication(authentication);
		return sc.getAuthentication();
	}

	public static boolean hasAuthority(Set<String> authorities, String authority) {
		if (authorities != null) {
			String authorityText = "," + authority + ",";
			for (String a : authorities) {
				if (authorityText.contains("," + a + ",")) { return true; }
			}
		}
		return false;
	}

	public static List<EAuthority> buildAuthority(String... allAuthorities) {
		List<EAuthority> all = new ArrayList<>();
		if (allAuthorities != null) {
			for (String authority : allAuthorities) {
				EAuthority a = new EAuthority();
				a.setName(authority);
				all.add(a);
			}
		}
		return all;
	}

	//	public static SecurityExpressionOperations get() {
	//		return new MethodSecurityExpressionRoot(getAuthentication());
	//	}
}
