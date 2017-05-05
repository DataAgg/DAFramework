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
 * author: carlos 2017年3月31日 11:28:25 remarks: 附件实体类
 */
@Table("sys_file")
public class EFile {
	@Id
	@Comment("主键")
	private Long id;

	@Column("name")
	@Comment("名称")
	@ColDefine(type = ColType.VARCHAR, width = 100, notNull = true)
	private String name;

	@Column("path")
	@Comment("路径")
	@ColDefine(type = ColType.VARCHAR, width = 200, notNull = true)
	private String path;

	@Column("del_flag")
	@Comment("删除标记（0：正常，1：删除）")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Default("0")
	private String delFlag;

	@Column("grouping")
	@Comment("分组")
	@ColDefine(type = ColType.VARCHAR, width = 64, notNull = true)
	private String grouping;
	
	@Column(hump=true)
	@Comment("文件来源类型")
	@ColDefine(type = ColType.CHAR, width = 1, notNull = false)
	private String sourceType;
	
	@Column(hump=true)
	@Comment("文件来源ID")
	@ColDefine(type = ColType.VARCHAR, width = 64, notNull = false)
	private String sourceId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

}
