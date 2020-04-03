package com.helpme.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="need")
public class NeedBean {

	@Id
	@GeneratedValue
	private int id;
	private String mobileno;
	private String needType;
	private String description;
	private String Status;
	
	public NeedBean() {
		// TODO Auto-generated constructor stub
	}

	
	public NeedBean(int id, String mobileno, String needType, String description, String status) {
		this.id = id;
		this.mobileno = mobileno;
		this.needType = needType;
		this.description = description;
		Status = status;
	}
	
	public NeedBean(String mobileno, String needType, String description, String status) {
		this.mobileno = mobileno;
		this.needType = needType;
		this.description = description;
		Status = status;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getNeedType() {
		return needType;
	}

	public void setNeedType(String needType) {
		this.needType = needType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "NeedBean [id=" + id + ", mobileno=" + mobileno + ", needType=" + needType + ", description=" + description
				+ ", Status=" + Status + "]";
	}
	
	
	
}
