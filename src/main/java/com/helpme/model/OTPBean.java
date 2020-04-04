package com.helpme.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

//{"OTP":"[Value1]", "COMPANY_NAME":"[Value2]", "Param3": "Value3"}
public class OTPBean {
	private String OTP;
	private String COMPANY_NAME;
	private String Param3;
	
	public OTPBean() {
	}

	public OTPBean(String oTP, String cOMPANY_NAME, String param3) {
		super();
		OTP = oTP;
		COMPANY_NAME = cOMPANY_NAME;
		Param3 = param3;
	}

	@JsonProperty("OTP")
	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	@JsonProperty("COMPANY_NAME")
	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}

	public void setCOMPANY_NAME(String cOMPANY_NAME) {
		COMPANY_NAME = cOMPANY_NAME;
	}

	@JsonProperty("Param3")
	public String getParam3() {
		return Param3;
	}

	public void setParam3(String param3) {
		Param3 = param3;
	}

	@Override
	public String toString() {
		return "OTPBean [OTP=" + OTP + ", COMPANY_NAME=" + COMPANY_NAME + ", Param3=" + Param3 + "]";
	}
	
	public String toJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	
	
}
