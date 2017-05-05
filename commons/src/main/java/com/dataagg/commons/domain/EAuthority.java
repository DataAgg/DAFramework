package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
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

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 64, notNull = true)
	@Comment("权限标识")
	private String name;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = true)
	@Comment("描述")
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
