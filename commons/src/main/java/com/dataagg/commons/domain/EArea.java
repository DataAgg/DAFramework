/**
 * author: loserStar
 * date: 2017年3月30日上午10:06:24
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
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

/**
 * author: loserStar date: 2017年3月30日上午10:06:24 email:362527240@qq.com
 * github:https://github.com/xinxin321198 remarks: 区域实体类
 */
@Table("sys_area")
public class EArea implements ITreeNode<EArea> {
	private static final long serialVersionUID = 1393610235911613287L;

	@Id
	@Comment("主键")
	private Long id;

	@Column("parent_id")
	@Comment("父类ID")
	@Default("0")
	private Long parentId;

	@Comment("所有父类ID,逗号拼接")
	@ColDefine(type = ColType.VARCHAR, width = 2000, notNull = true)
	@Default(",0,")
	@Column("parent_ids")
	private String parentIds;

	@Comment("名称")
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = true)
	@Column("name")
	private String name;

	@Comment("全称")
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = true)
	@Column("full_name")
	@Default("${name}")
	private String fullName;

	@Comment("区域编号")
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = false)
	@Column("code")
	private String code;

	@Comment("区域类型")
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = true)
	@Column("type")
	private String type;

	@Column("sort")
	@Comment("排序(升序)")
	@ColDefine(type = ColType.INT, width = 10, notNull = true)
	@Default("0")
	private int sort;

	@Comment("删除标记（0：正常，1：删除）")
	@Column("del_flag")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Default("0")
	private String delFlag;

	private List<EArea> items;

	private List<Long> pids;
	

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Long getParentId() {
		// TODO Auto-generated method stub
		return parentId;
	}

	@Override
	public void setParentId(Long parentId) {
		// TODO Auto-generated method stub
		this.parentId = parentId;
	}

	@Override
	public String getParentIds() {
		// TODO Auto-generated method stub
		return parentIds;
	}

	@Override
	public void setParentIds(String parentIds) {
		// TODO Auto-generated method stub
		this.parentIds = parentIds;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public void setCode(String code) {
		// TODO Auto-generated method stub
		this.code = code;
	}

	@Override
	public List<EArea> getItems() {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public void setItems(List<EArea> items) {
		// TODO Auto-generated method stub
		this.items = items;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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
