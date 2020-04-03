package com.helpme.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="user")
public class UserBean {

	@Id
	private String mobileno;
	private String fullname;
	private String address;
	private String state;
	private String city;
	private boolean cough;
	private boolean fever;
	private boolean breath;
	private String otherMedicalCondition;
	private int age;
	private String travel;
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}

	public UserBean(String mobileno, String fullname, String address, String state, String city, boolean cough, boolean fever,
			boolean breath, String otherMedicalCondition, int age, String travel) {
		this.mobileno = mobileno;
		this.fullname = fullname;
		this.address = address;
		this.state = state;
		this.city = city;
		this.cough = cough;
		this.fever = fever;
		this.breath = breath;
		this.otherMedicalCondition = otherMedicalCondition;
		this.age = age;
		this.travel = travel;
	}

	
	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isCough() {
		return cough;
	}

	public void setCough(boolean cough) {
		this.cough = cough;
	}

	public boolean isFever() {
		return fever;
	}

	public void setFever(boolean fever) {
		this.fever = fever;
	}

	public boolean isBreath() {
		return breath;
	}

	public void setBreath(boolean breath) {
		this.breath = breath;
	}

	public String getOtherMedicalCondition() {
		return otherMedicalCondition;
	}

	public void setOtherMedicalCondition(String otherMedicalCondition) {
		this.otherMedicalCondition = otherMedicalCondition;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String isTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
	}

	@Override
	public String toString() {
		return "UserBean [mobileno=" + mobileno + ", fullname=" + fullname + ", address=" + address + ", state=" + state + ", city="
				+ city + ", cough=" + cough + ", fever=" + fever + ", breath=" + breath + ", otherMedicalCondition="
				+ otherMedicalCondition + ", age=" + age + ", travel=" + travel + "]";
	}

	
	
	
	
}
