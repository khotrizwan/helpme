package com.helpme.model;

public class MedicalDetailsBean {

	private String medicalContidions;
	private String medicalDescription;
	private int age;
	private String travelHistory;
	
	public MedicalDetailsBean() {
		// TODO Auto-generated constructor stub
	}

	
	public MedicalDetailsBean(String medicalContidions, String medicalDescription, int age, String travelHistory) {
		super();
		this.medicalContidions = medicalContidions;
		this.medicalDescription = medicalDescription;
		this.age = age;
		this.travelHistory = travelHistory;
	}


	public String getMedicalContidions() {
		return medicalContidions;
	}

	public void setMedicalContidions(String medicalContidions) {
		this.medicalContidions = medicalContidions;
	}

	public String getMedicalDescription() {
		return medicalDescription;
	}

	public void setMedicalDescription(String medicalDescription) {
		this.medicalDescription = medicalDescription;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTravelHistory() {
		return travelHistory;
	}

	public void setTravelHistory(String travelHistory) {
		this.travelHistory = travelHistory;
	}


	@Override
	public String toString() {
		return "MedicalDetailsBean [medicalContidions=" + medicalContidions + ", medicalDescription="
				+ medicalDescription + ", age=" + age + ", travelHistory=" + travelHistory + "]";
	}
	
	
}
