package com.dataagg.security.config;

import com.dataagg.security.filter.JwtAuthenticationTokenFilter;
import com.dataagg.security.service.security.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by watano on 2017/3/12.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${api.csrftoken}")
	private String csrfTokenApi;

	@Value("${api.login}")
	private String loginApi;

	@Value("${api.logout}")
	private String logoutApi;

	@Autowired
	private SysUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 基于token，所以不需要session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers(csrfTokenApi).permitAll()
				// 对于获取token的rest api要允许匿名访问
				.antMatchers("/anonymous/**").permitAll()

				.antMatchers("/api/user/**").access("hasAuthority('USER')")
				.antMatchers("/api/admin/**").access("hasAuthority('ADMIN')")
				.antMatchers("/api/dba/**").access("hasAuthority('DBA')")
				.antMatchers("/api/**").fullyAuthenticated()
				// 除上面外的所有请求全部需要鉴权认证
				.anyRequest().authenticated()
				.and().formLogin().loginProcessingUrl(loginApi)
				.successHandler(new RestAuthenticationSuccessHandler())
				.failureHandler(new RestAuthenticationFailureHandler())
				.and().logout().logoutUrl(logoutApi)
				.logoutSuccessHandler(new RestLogoutSuccessHandler())
				.and().exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
				.accessDeniedHandler(new RestAccessDeniedHandler());
		// 添加JWT filter
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
}