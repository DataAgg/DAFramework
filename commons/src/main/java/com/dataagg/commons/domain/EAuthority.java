package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by watano on 2017/3/13.
 */
@Table("sys_authority")
@Comment("权限标识")
public class EAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 7423414401567180611L;
	@Id
	private Long id;
	@Column
	private String name;

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

	@Override
	public String getAuthority() {
		return name;
	}
}
