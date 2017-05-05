package com.dataagg.commons.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("da_account")
@Comment("账户信息")
public class EAccount {
	@Id
	@Comment("主键")
	private Long id;

	@Comment("删除标记（0：正常，1：删除）")
	@Column("del_flag")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Default("0")
	private String delFlag;

	
	@Column("org_id")
	@Comment("所属机构Id")
	@ColDefine(type = ColType.INT,width=20,notNull=true)
	private Long orgId;
	
	@Comment("所属机构的名称")
	private String orgName;
	
	@Column("user_id")
	@ColDefine(notNull=true)
	@Comment("关联用户")
	private Long userId;
	
	@One(field="userId")
	private EUser user;
	
	@Column(hump=true)
	@ColDefine(type = ColType.VARCHAR,width=128)
	@Comment("用户全名")
	private String fullName;
	
	@Column(hump=true)
	@ColDefine(type = ColType.VARCHAR,width=128)
	@Comment("手机")
	private String mobile;
	
	@Column(hump=true)
	@ColDefine(type = ColType.VARCHAR,width=128)
	@Comment("email")
	private String email;
	
	@Column(hump=true)
	@ColDefine(type = ColType.VARCHAR,width=128)
	@Comment("地址")
	private String address;
	
	@Column(hump=true)
	@ColDefine(type = ColType.VARCHAR,width=128)
	@Comment("身份证号")
	private String number;
	
	@Column(hump=true)
	@ColDefine(type = ColType.VARCHAR,width=512)
	@Comment("备注")
	private String comment;
	
	//角色列表
	private List<Long> roleList;
	
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

	public EUser getUser() {
		return user;
	}

	public void setUser(EUser user) {
		this.user = user;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public List<Long> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
