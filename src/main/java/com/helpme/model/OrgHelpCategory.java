package com.helpme.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="org_help_category_map")
public class OrgHelpCategory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int organizationId;
	private int helpCategoryId;
	private String isActive;
	private Date createDate;
	
	public OrgHelpCategory() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public int getHelpCategoryId() {
		return helpCategoryId;
	}
	public void setHelpCategoryId(int helpCategoryId) {
		this.helpCategoryId = helpCategoryId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}
