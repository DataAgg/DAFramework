/**
 * author: loserStar
 * date: 2017年3月30日上午10:06:24
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.dataagg.commons.domain;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * author: loserStar
 * date: 2017年3月30日上午10:06:24
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks: 数据字典实体类
 */
@Table("sys_dict")
public class EDict {
	@Id
	@Comment("主键")
	private Long id;
	
	@Column("type")
	@Comment("类型")
	@ColDefine(type=ColType.VARCHAR, width=100, notNull=true)
	private String type;
	
	@Column("value")
	@Comment("数据值")
	@ColDefine(type=ColType.VARCHAR, width=100, notNull=true)
	private String value;
	
	@Column("label")
	@Comment("标签名")
	@ColDefine(type=ColType.VARCHAR, width=100, notNull=true)
	private String label;

	@Column("description")
	@Comment("描述")
	@ColDefine(type=ColType.VARCHAR, width=100)
	private String description;
	
	@Column("sort")
	@Comment("排序(升序)")
	@ColDefine(type=ColType.INT, width=10, notNull=true)
	@Default("0")
	private int sort;
	
	@Comment("删除标记（0：正常，1：删除）")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Column("del_flag")
	@Default("0")
	private String delFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
	
}
