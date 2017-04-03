package com.dataagg.utils;

import java.util.Arrays;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class OAuth2RestHelper {
	private String clientId;
	private String clientSecret;
	private String accessTokenUri = "/oauth/token";
	private String userAuthorizationUri = "/oauth/authorize";
	private OAuth2RestTemplate restTemplate;

	public OAuth2RestHelper(String clientId, String clientSecret) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public static OAuth2RestHelper github(String clientId, String clientSecret) {
		OAuth2RestHelper helper = new OAuth2RestHelper(clientId, clientSecret);
		return helper;
	}

	public OAuth2ProtectedResourceDetails password(String userName, String pswd, String... scopes) {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setAccessTokenUri(accessTokenUri);
		resource.setClientId(clientId);
		resource.setClientSecret(clientSecret);
		resource.setGrantType("password");
		resource.setUsername(userName);
		resource.setPassword(pswd);
		resource.setScope(Arrays.asList(scopes));
		return resource;
	}

	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource) {
		AccessTokenRequest atr = new DefaultAccessTokenRequest();
		restTemplate = new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(atr));
		return restTemplate.getAccessToken();
	}
}
