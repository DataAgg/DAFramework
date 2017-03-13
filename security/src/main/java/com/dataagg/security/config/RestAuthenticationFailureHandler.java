package com.dataagg.security.config;

import com.dataagg.security.model.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Logger log = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.info("auth failure!");
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.addError(exception);
        jsonResponse.write(response);
    }
}
