package com.dataagg.util.oauth;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;

/**
 * Created by watano on 2017/3/20.
 */
public class WXAuthorizationCodeAccessTokenProvider extends AuthorizationCodeAccessTokenProvider {
	public WXAuthorizationCodeAccessTokenProvider(){
		super();
		setAuthorizationRequestEnhancer((request, resource, form, headers) -> {
			form.set("appid", resource.getClientId());
			form.set("secret", resource.getClientSecret());
			form.set("scope", "snsapi_userinfo");
			form.set("response_type", "code");
			form.set("#wechat_redirect", "");
		});
	}
}
