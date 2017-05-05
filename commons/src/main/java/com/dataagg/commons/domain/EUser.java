package com.dataagg.commons.domain;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.security.core.userdetails.UserDetails;

@Table("sys_user")
@Comment("用户信息")
public class EUser implements UserDetails {
	private static final long serialVersionUID = 1485837934469013198L;

	@Id
	private Long id;

	@Column(hump = true, value = "username")
	@ColDefine(type = ColType.VARCHAR, width = 64, notNull = true)
	private String username;

	@Column
	private String password;
	
	private String oldPassword;

	@Column("org_id")
	@Comment("所属机构Id")
	@ColDefine(type = ColType.INT, width = 20, notNull = true)
	private Long orgId;
	
	@Comment("所属机构类型")
	private String orgType;
	
	@Comment("删除标记（0：正常，1：删除）")
	@Column("del_flag")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Default("0")
	private String delFlag;

	private List<EAuthority> grantedAuthorities = new ArrayList<>();

	@ManyMany(relation = "sys_user_role", from = "userid", to = "roleid")
	private List<ERole> roles = new ArrayList<>();

	@One(field = "id", key = "userId")
	private EAccount account;

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setGrantedAuthorities(List<EAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	@Override
	public List<EAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ERole> getRoles() {
		return roles;
	}

	public void setRoles(List<ERole> roles) {
		this.roles = roles;
	}

	public EAccount getAccount() {
		return account;
	}

	public void setAccount(EAccount account) {
		this.account = account;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}



	@Override
	public String toString() {
		return "EUser{" + "id=" + id + ", username='" + username + "\'}";
	}
}
