package com.helpme.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="organization")
public class OrgBean {
	@Id
	@GeneratedValue
	private int id;
	private String orgName;
	private String orgDesc;
	private String mobileNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailAddress;
	private String latitude;
	private String longitude;
	private String address;
	private int cityId;
	private String orgType; //service_provider / Volunteer / HelpmePlease / HelpFinder
	private String isIndividual;
	private String canAccept;
	private String isActive;
	private Date createDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDesc() {
		return orgDesc;
	}
	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getIsIndividual() {
		return isIndividual;
	}
	public void setIsIndividual(String isIndividual) {
		this.isIndividual = isIndividual;
	}
	public String getCanAccept() {
		return canAccept;
	}
	public void setCanAccept(String canAccept) {
		this.canAccept = canAccept;
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
	
	public OrgBean() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrgBean [id=" + id + ", orgName=" + orgName + ", orgDesc=" + orgDesc + ", mobileNumber=" + mobileNumber
				+ ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", emailAddress=" + emailAddress + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", address=" + address + ", cityId=" + cityId + ", orgType=" + orgType + ", isIndividual="
				+ isIndividual + ", canAccept=" + canAccept + ", isActive=" + isActive + ", createDate=" + createDate
				+ "]";
	}
	
	
	
	
}
