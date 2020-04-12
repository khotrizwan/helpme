package com.helpme.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity(name="user")
public class UserBean {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(unique=true, length=10)
	private String mobileno;
	
	@Size(min = 3, max = 150)
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailAddress;
	private String latitude;
	private String longitude;
	private String address;
	private int cityId;
	private String isAdmin;
	private String userType; //help_finder / service provider / volunteer / HelpMePlease 
	private int organizationId;
	private String isActive;
	private Date createDate;
	private String deviceToken;
	private String language;
	
	@Transient
	private String otp;

	
	public UserBean() {
		// TODO Auto-generated constructor stub
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
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

	
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", mobileno=" + mobileno + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", emailAddress=" + emailAddress + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", address=" + address + ", cityId=" + cityId + ", isAdmin=" + isAdmin
				+ ", userType=" + userType + ", organizationId=" + organizationId + ", isActive=" + isActive
				+ ", createDate=" + createDate + ", deviceToken=" + deviceToken + ", language=" + language + ", otp="
				+ otp + "]";
	}

}

