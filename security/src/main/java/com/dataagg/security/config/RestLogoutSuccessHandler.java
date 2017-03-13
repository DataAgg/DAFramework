package com.dataagg.security.config;

import com.dataagg.security.model.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private final Logger log = LoggerFactory.getLogger(RestLogoutSuccessHandler.class);
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
	    log.info("logout success!");
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.addMsg("result", "success");
        jsonResponse.write(response);
    }
}
