package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

import java.util.Map;

/**
 * Created by watano on 2017/3/20.
 */
@Table("sys_access_token")
@Comment("OAuth2 Access Token")
public class EAccessToken {

	@Id
	private Long id;
	@Column
	private Long openUserId;
	@One(field = "openUserId")
	private EOpenUser openUser;
	@Column
	private String remoteAddress;
	@Column
	private String sessionId;
	@Column
	private String tokenValue;
	@Column
	private String tokenType;
	@Column
	private String refreshToken;
	@Column
	private Integer expiresIn;
	@Column
	private String scopes;

	private Map<String, Object> details;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOpenUserId() {
		return openUserId;
	}

	public void setOpenUserId(Long openUserId) {
		this.openUserId = openUserId;
	}

	public EOpenUser getOpenUser() {
		return openUser;
	}

	public void setOpenUser(EOpenUser openUser) {
		this.openUser = openUser;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}


}
