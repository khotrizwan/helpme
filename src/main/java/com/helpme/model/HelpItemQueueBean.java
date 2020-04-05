package com.helpme.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="help_item_queue")
public class HelpItemQueueBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int helpItemId;
	private int userId;
	
	public HelpItemQueueBean() {
		// TODO Auto-generated constructor stub
	}

	public int getHelpItemId() {
		return helpItemId;
	}

	public void setHelpItemId(int helpItemId) {
		this.helpItemId = helpItemId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
