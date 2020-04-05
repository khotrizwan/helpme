package com.helpme.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="help_history_item")
public class HelpHistoryBean {
	@Id
	@GeneratedValue
	private int id;
	private int helpItemId;
	private String helpText;
	private String helpItemStatus; //OPEN/ACCEPTED/IN_PROGRESS/REJECTED/RESOLVED/CLOSED/UN_RESOLVED
	private Date createDate;
	private int createdBy;
	
	public HelpHistoryBean() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHelpItemId() {
		return helpItemId;
	}

	public void setHelpItemId(int helpItemId) {
		this.helpItemId = helpItemId;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "HelpHistoryBean [id=" + id + ", help_item_id=" + help_item_id + ", helpText=" + helpText
				+ ", helpItemStatus=" + helpItemStatus + ", createDate=" + createDate + ", createdBy=" + createdBy
				+ "]";
	}
	
	
}
