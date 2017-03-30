package com.dataagg.utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

	//	public static SecurityExpressionOperations get() {
	//		return new MethodSecurityExpressionRoot(getAuthentication());
	//	}
}
