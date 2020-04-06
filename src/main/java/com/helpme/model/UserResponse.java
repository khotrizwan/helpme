package com.helpme.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("otp")
public class UserResponse extends ResponseBean {

	UserBean userDetails;
	
	
	public UserResponse() {
		// TODO Auto-generated constructor stub
	}

	public UserBean getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserBean userDetails) {
		this.userDetails = userDetails;
	}
	
	
	
}
