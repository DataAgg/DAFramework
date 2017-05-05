package com.dataagg.util.oauth;

import com.dataagg.commons.domain.EAccessToken;
import com.dataagg.commons.domain.EOpenUser;
import com.google.gson.Gson;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by watano on 2017/3/19.
 */
public class OAuth2Util {
	private final static Logger log = LoggerFactory.getLogger(OAuth2Util.class);

	public static Filter wechat(AuthorizationCodeResourceDetails client, ResourceServerProperties resourceServerProperties, String path, OAuth2ClientContext oauth2ClientContext) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path);

		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client, oauth2ClientContext);
		AuthorizationCodeAccessTokenProvider accessTokenProvider = new AuthorizationCodeAccessTokenProvider();
		accessTokenProvider.setAuthorizationRequestEnhancer((request, resource, form, headers) -> {
			form.set("appid", resource.getClientId());
			form.set("secret", resource.getClientSecret());
			form.set("scope", "snsapi_userinfo");
			form.set("response_type", "code");
			form.set("#wechat_redirect", "");
		});
		accessTokenProvider.setMessageConverters(converters());
		oAuth2RestTemplate.setAccessTokenProvider(accessTokenProvider);

		oAuth2RestTemplate.setRetryBadAccessTokens(true);
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);

		UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), client.getClientId());
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		return oAuth2ClientAuthenticationFilter;
	}

	public static Filter general(AuthorizationCodeResourceDetails client, ResourceServerProperties resourceServerProperties, String path, OAuth2ClientContext oauth2ClientContext) {
		OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationFilter = new OAuth2ClientAuthenticationProcessingFilter(path){
			protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			                                        FilterChain chain, Authentication authResult) throws IOException, ServletException {
				super.successfulAuthentication(request, response, chain, authResult);
				OAuth2AccessToken accessToken = restTemplate.getAccessToken();
				log.warn(new Gson().toJson(authResult));
				log.warn(new Gson().toJson(accessToken));
			}
		};
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client, oauth2ClientContext);
		oAuth2ClientAuthenticationFilter.setRestTemplate(oAuth2RestTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), client.getClientId());
		tokenServices.setRestTemplate(oAuth2RestTemplate);
		oAuth2ClientAuthenticationFilter.setTokenServices(tokenServices);
		return oAuth2ClientAuthenticationFilter;
	}

	public static List<HttpMessageConverter<?>> converters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new GsonHttpMessageConverter());
		return converters;
	}

	@SuppressWarnings("unchecked")
	public static EOpenUser fetch(OAuth2Authentication oAuth2Authentication){
		EOpenUser openUser = new EOpenUser();
		openUser.setClientId(oAuth2Authentication.getOAuth2Request().getClientId());
		if(oAuth2Authentication.getUserAuthentication().getDetails() != null){
			if(oAuth2Authentication.getUserAuthentication().getDetails() instanceof Map){
				Map<Object, Object> userDetails = (Map<Object, Object>) oAuth2Authentication.getUserAuthentication().getDetails();
				if((userDetails.get("id") != null)){
					openUser.setOpenId(userDetails.get("id").toString());
				}
			}
		}
		openUser.setClientName(oAuth2Authentication.getName());
		return openUser;
	}

	public static EAccessToken fetch(OAuth2Authentication oAuth2Authentication, OAuth2AccessToken accessToken){
		EAccessToken eAccessToken = new EAccessToken();
		eAccessToken.setOpenUser(fetch(oAuth2Authentication));

		Object details = oAuth2Authentication.getDetails();
		if(details instanceof OAuth2AuthenticationDetails){
			OAuth2AuthenticationDetails details1 = (OAuth2AuthenticationDetails) details;
			eAccessToken.setRemoteAddress(details1.getRemoteAddress());
			eAccessToken.setSessionId(details1.getSessionId());
		}
		eAccessToken.setTokenType(accessToken.getTokenType());
		eAccessToken.setTokenValue(accessToken.getValue());
		eAccessToken.setExpiresIn(accessToken.getExpiresIn());
		if (accessToken.getRefreshToken() != null) {
			eAccessToken.setRefreshToken(accessToken.getRefreshToken().getValue());
		}
		if (accessToken.getScope() != null) {
			String scopes = Strings.join2("|", accessToken.getScope().toArray(new String[]{}));
			eAccessToken.setScopes(scopes);
		}
		return eAccessToken;
	}
}
