/**
 * author: loserStar
 * date: 2017年3月28日下午5:09:01
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.dataagg.commons.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.dataagg.commons.domain.EArea;
import com.dataagg.util.ITreeNode;

/**
 * author: loserStar date: 2017年3月28日下午5:09:01 email:362527240@qq.com
 * github:https://github.com/xinxin321198 remarks:机构
 */

@Table("da_org")
@Comment("机构")
public class EOrg implements ITreeNode<EOrg>{
	private static final long serialVersionUID = 1953432379817392190L;

	@Id(auto = true)
	@Comment("主键")
	private Long id;

	@Column(hump = true)
	@ColDefine(type = ColType.CHAR, width = 1, notNull = true)
	@Default(value = "0")
	@Comment("删除标记（0：正常，1：删除）")
	private String delFlag;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 1024, notNull = false)
	@Comment("备注")
	private String remarks;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 256, notNull = true)
	@Comment("机构名称")
	private String name;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 256, notNull = false)
	@Comment("机构代码")
	private String code;
 
	@Column(hump = true)
	@Comment("所属行政区域")
	@ColDefine(notNull = true)
	private Long areaId;
	
	private EArea area;

	@Column(hump = true)
	@Comment("上级机构")
	@Default("0")
	private Long parentId;
	
	@Comment("所有父类ID,逗号拼接")
	@ColDefine(type = ColType.VARCHAR, width = 2000, notNull = true)
	@Default(",0,")
	@Column("parent_ids")
	private String parentIds;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 256, notNull = false)
	@Comment("营业执照号")
	private String licenseNumber;

	@Column(hump = true)
	@Comment("企业性质")
	@ColDefine(type = ColType.CHAR, width = 2, notNull = false)
	private String nature;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 128, notNull = false)
	@Comment("法定代表人")
	private String legal;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 128, notNull = false)
	@Comment("联系人")
	private String person;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 128, notNull = false)
	@Comment("座机")
	private String phone;

	@ColDefine(type = ColType.VARCHAR, width = 128, notNull = false)
	@Column(hump = true)
	@Comment("手机")
	private String mobile;

	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 128, notNull = false)
	@Comment("传真")
	private String fax;

	private List<EOrg> items;

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
	public List<EOrg> getItems() {
		// TODO Auto-generated method stub
		return items;
	}

	@Override
	public void setItems(List<EOrg> items) {
		// TODO Auto-generated method stub
		this.items = items;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public EArea getArea() {
		return area;
	}

	public void setArea(EArea area) {
		this.area = area;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
 

}
