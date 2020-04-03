package com.helpme.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="login")
public class LoginBean {
	@Id
	private String mobileno;
	private String otp;
	
	public LoginBean() {
		// TODO Auto-generated constructor stub
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LoginBean(String mobileno, String otp) {
		super();
		this.mobileno = mobileno;
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "LoginBean [mobileno=" + mobileno + ", otp=" + otp + "]";
	}
	
	

}
