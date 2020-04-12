package com.helpme.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity(name="login")
public class LoginBean {
	@Id
	@Size(min = 10, max = 10)
	private String mobileno;
	private String otp;
	@Transient
	private String deviceToken;
	@Transient
	private String latitude;
	@Transient
	private String longitude;
	
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

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "LoginBean [mobileno=" + mobileno + ", otp=" + otp + ", deviceToken=" + deviceToken + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}

	
}
