package com.dataagg.security.config;

import com.dataagg.security.model.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by watano on 2017/3/13.
 */
public class SysAuthenticationHandler implements AccessDeniedHandler {
	private final Logger log = LoggerFactory.getLogger(SysAuthenticationHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.warn("Authentication Failed: " + accessDeniedException.getMessage());
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.addError("invalid_parameter.name", accessDeniedException.getMessage());
		jsonResponse.write(response);
	}
}
