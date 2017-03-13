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
 * 拒绝访问
 *
 * @author XueLiang
 * @version 1.0
 * @date 2017年3月1日 上午11:07:10
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
	private final Logger log = LoggerFactory.getLogger(RestAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.warn("Authentication Failed: " + accessDeniedException.getMessage());
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.addError(accessDeniedException);
		jsonResponse.write(response);
	}
}
