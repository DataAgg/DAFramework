package com.dataagg.security.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.dataagg.commons.domain.EUser;
import com.dataagg.security.service.SysUserDetailsService;

/**
 * Created by samchu on 2017/2/15.
 */
@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	@Autowired
	private CustomUserDetailsAuthenticationProvider customUserDetailsAuthenticationProvider;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private SysUserDetailsService userDetailsService;

	private AuthenticationManager myAuthenticationManager = authentication -> {
		EUser userDetails = userDetailsService.fetchFullByName(authentication.getName());
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info(">> WebSecurityConfiguration.configure AuthenticationManagerBuilder={}", auth);
		auth.authenticationProvider(customUserDetailsAuthenticationProvider);
		log.info("<< WebSecurityConfiguration.configure AuthenticationManagerBuilder");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}

	@Bean
	public TokenStore tokenStore() {
		JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
		return jdbcTokenStore;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("ASDFASFsdfsdfsdfsfadsf234asdfasfdas");
		// 註解掉的原因是因為跟原本的一樣，但記錄一下如果需要特別調整可以在這調
		// jwtAccessTokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
		return jwtAccessTokenConverter;
	}

	@Bean
	public ClientDetailsService jdbcClientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

	@Bean
	public DefaultTokenServices getDefaultTokenServices() throws Exception {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setAuthenticationManager(myAuthenticationManager);
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
		tokenServices.setClientDetailsService(clientDetailsService);
		tokenServices.setSupportRefreshToken(true);
		return tokenServices;
	}
}
