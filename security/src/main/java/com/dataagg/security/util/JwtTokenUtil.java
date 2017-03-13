package com.dataagg.security.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by watano on 2017/3/13.
 */
public class JwtTokenUtil {
	private final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

	public String getUsernameFromToken(String authToken) {
		return null;
	}

	public boolean validateToken(String authToken, UserDetails userDetails) {
		return true;
	}
}
