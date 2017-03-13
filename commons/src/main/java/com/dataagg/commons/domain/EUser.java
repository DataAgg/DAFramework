package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Table("sys_user")
@Comment("用户信息")
public class EUser implements UserDetails {
	@Id
	private Long id;

	@Name
	@Column("user_name")
	private String username;

	@Column
	private String password;

	private List<EAuthority> grantedAuthorities = new ArrayList<>();

	@ManyMany(relation = "sys_user_role", from = "userid", to = "roleid")
	private List<ERole> roles = new ArrayList<>();

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

	@Override
	public String toString() {
		return "EUser{" + "id=" + id + ", username='" + username + "\'}";
	}
}
