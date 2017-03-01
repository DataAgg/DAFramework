package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("da_account")
@Comment("账户信息")
public class EAccount {
	@Id
	private Long id;
	@Column
	private Long userId;
	@One(field = "userId")
	private EUser EUser;
	@Column("full_name")
	private String fullName;
	@Column("mobile")
	private String mobile;
	@Column("address")
	private String address;
	@Column("comment")
	private String comment;

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

	public com.dataagg.commons.domain.EUser getEUser() {
		return EUser;
	}

	public void setEUser(com.dataagg.commons.domain.EUser EUser) {
		this.EUser = EUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
