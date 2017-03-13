package com.dataagg.security.config;

import com.dataagg.security.model.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个入口点其实仅仅是被ExceptionTranslationFilter引用
 * 由此入口决定redirect、forward的操作
 * @author XueLiang
 * @date 2017年3月1日 上午10:20:39
 * @version 1.0
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        log.warn("Authentication Failed: " + authException.getMessage());
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.addError(authException);
        jsonResponse.write(response);
    }
}
