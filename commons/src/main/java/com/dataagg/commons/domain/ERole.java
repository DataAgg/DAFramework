package com.dataagg.commons.domain;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by watano on 2017/3/13.
 */
@Table("sys_role")
@Comment("角色信息")
public class ERole {
	@Id
	@Comment("主键")
	private Long id;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 64, notNull = true)
	@Comment("角色名")
	private String name;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = false)
	@Comment("描述")
	private String description;

	@ManyMany(relation = "sys_role_authority", from = "role_id", to = "authority_id")
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

	public void setAuthorities(List<EAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
