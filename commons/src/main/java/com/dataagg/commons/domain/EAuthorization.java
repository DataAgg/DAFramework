package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by watano on 2017/3/1.
 */
@Table("sys_authorization")
@Comment("OAuth2认证信息")
public class EAuthorization {
	@Id
	private Long id;
	@Column
	private Long userId;
	@One(field = "userId")
	private EUser EUser;
	@Column("access_token")
	private String accessToken;
	@Column("expires_in")
	private Long expiresIn = 3600L;
	@Column("token_type")
	private String tokenType;
	@Column("scope")
	private String scope;
	@Column("refresh_token")
	private String refreshToken;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public EUser getEUser() {
		return EUser;
	}

	public void setEUser(EUser EUser) {
		this.EUser = EUser;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
