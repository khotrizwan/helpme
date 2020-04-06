package com.helpme.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="help_item")
public class HelpBean {

	@Id
	@GeneratedValue
	private int id;
	private int userId;
	private int helpCategoryId;
	private String helpText;
	private String helpItemStatus; //OPEN/ACCEPTED/REJECTED/RESOLVED/CLOSED/UN_RESOLVED
	private int assignedUserId;
	private int volunteerUserId;
	private Date createDate;
	private Date updateDate;
	private int updateBy;
	
	public HelpBean() {
		// TODO Auto-generated constructor stub
	}
	public HelpBean(HelpBean helpBean) {
		this.id = helpBean.getId();
		this.userId = helpBean.getUserId();
		this.helpCategoryId = helpBean.getHelpCategoryId();
		this.helpText = helpBean.getHelpText();
		this.helpItemStatus = helpBean.getHelpItemStatus();
		this.assignedUserId = helpBean.getAssignedUserId();
		this.volunteerUserId = helpBean.getVolunteerUserId();
		this.createDate = helpBean.getCreateDate();
		this.updateDate = helpBean.getUpdateDate();
		this.updateBy = helpBean.getUpdateBy();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getHelpCategoryId() {
		return helpCategoryId;
	}
	public void setHelpCategoryId(int helpCategoryId) {
		this.helpCategoryId = helpCategoryId;
	}
	public String getHelpText() {
		return helpText;
	}
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	public String getHelpItemStatus() {
		return helpItemStatus;
	}
	public void setHelpItemStatus(String helpItemStatus) {
		this.helpItemStatus = helpItemStatus;
	}
	public int getAssignedUserId() {
		return assignedUserId;
	}
	public void setAssignedUserId(int assignedUserId) {
		this.assignedUserId = assignedUserId;
	}
	public int getVolunteerUserId() {
		return volunteerUserId;
	}
	public void setVolunteerUserId(int volunteerUserId) {
		this.volunteerUserId = volunteerUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}	
	public int getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}
	
	@Override
	public String toString() {
		return "HelpBean [id=" + id + ", userId=" + userId + ", helpCategoryId=" + helpCategoryId + ", helpText="
				+ helpText + ", helpItemStatus=" + helpItemStatus + ", assignedUserId=" + assignedUserId
				+ ", volunteerUserId=" + volunteerUserId + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", updateBy=" + updateBy + "]";
	}

}
