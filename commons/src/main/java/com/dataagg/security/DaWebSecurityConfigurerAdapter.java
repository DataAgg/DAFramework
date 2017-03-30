package com.dataagg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class DaWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Autowired
	private SysUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**").permitAll()
			.anyRequest().authenticated()
			.and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
			.and().logout().logoutSuccessUrl("/").permitAll()
			.and().csrf()//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			//.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
			.and().userDetailsService(userDetailsService)
			;
		// @formatter:on
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
}
