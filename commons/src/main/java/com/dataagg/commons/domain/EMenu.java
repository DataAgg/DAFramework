package com.dataagg.commons.domain;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.dataagg.util.ITreeNode;

import jodd.util.StringUtil;

@Table("sys_menus")
@Comment("系统菜单")
public class EMenu implements ITreeNode<EMenu> {
	private static final long serialVersionUID = -5290705660038470879L;
	@Id
	@Comment("主键")
	private Long id;
	@Column
	@Comment("父级节点ID")
	@Default("0")
	private Long parentId;
	@Comment("所有父类ID,逗号拼接")
	@ColDefine(type = ColType.VARCHAR,width=2000,notNull=true)
	@Default(",0,")
	@Column("parent_ids")
	private String parentIds;
	@Column
	@Comment("path")
	private String code;
	@Column
	@Comment("名称")
	private String name;
	@Column
	@Comment("图标")
	private String icon = "message";
	@Column
	@Comment("类型")
	@ColDefine(type = ColType.VARCHAR, width = 10, notNull = true)
	@Default("1")
	private String menuType = "1";//1:菜单项; 10:一级子菜单; 20:二级子菜单
	@Column
	@Comment("排序")
	private int sort = 10;
	@Column
	@Comment("标记")
	@Default("1")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	private String flag = "1";//1: 启用, 0:停用
	
	@Comment("删除标记（0：正常，1：删除）")
	@Column("del_flag")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Default("0")
	private String delFlag = "0";//1: 删除, 0:未删除

	@Column
	@Comment("权限")
	private String auauthorities = "";

	private List<EMenu> items;

	private List<Long> pids;
	
	public EMenu() {
		super();
	}

	@Override
	public String toString() {
		return "id:" + id + ", parentId:" + parentId + ", code:" + code + ", name:" + name + ", icon:" + icon + ", menuType:" + menuType + ", sort:" + sort + ", flag:" + flag;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Override
	public String getParentIds() {
		return parentIds;
	}

	@Override
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<EMenu> getItems() {
		return items;
	}

	@Override
	public void setItems(List<EMenu> items) {
		this.items = items;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getAuauthorities() {
		return auauthorities;
	}

	public void setAuauthorities(String auauthorities) {
		this.auauthorities = auauthorities;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public List<Long> getPids() {
		if (!StringUtil.equals(parentIds, ",0,")) {
			pids = new ArrayList<>();
			parentIds = StringUtil.replace(parentIds, ",0,", "");
			String[] pidsStr = StringUtil.split(parentIds, ",");
			for (String pidStr : pidsStr) {
				if (StringUtil.isNotBlank(pidStr) && !StringUtil.equals(pidStr, "0")) {
					pids.add(Long.parseLong(pidStr));
				}
			}
			return pids;
		}
		return new ArrayList<>();
	}

	public void setPids(List<Long> pids) {
		this.pids = pids;
	}
	
}
