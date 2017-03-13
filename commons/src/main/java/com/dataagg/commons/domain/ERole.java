package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by watano on 2017/3/13.
 */
@Table("sys_role")
@Comment("角色信息")
public class ERole {
	@Id
	private Long id;
	@Column
	private String name;

	@ManyMany(relation = "sys_role_authority", from = "roleid", to = "authorityId")
	private List<EAuthority> authorities = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<EAuthority> roles) {
		this.authorities = roles;
	}
}
