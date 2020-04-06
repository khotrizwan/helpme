package com.helpme.model;

import java.util.List;

public class UserListResponse extends ResponseBean{

	private List<UserBean> users;
	
	public UserListResponse() {
		super();
	}
	
	public List<UserBean> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserBean> users) {
		this.users = users;
	}
}
