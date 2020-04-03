package com.helpme.model;

import java.util.List;
import java.util.Map;

public class NeedMapResponse extends ResponseBean{
	
	Map<String, List<NeedBean>> data;
	public NeedMapResponse() {
		// TODO Auto-generated constructor stub
	}
	public Map<String, List<NeedBean>> getMap() {
		return data;
	}
	public void setMap(Map<String, List<NeedBean>> data) {
		this.data = data;
	}
	public NeedMapResponse(String errCode, String errMsg, Map<String, List<NeedBean>> data) {
		super(errCode, errMsg);
		this.data = data;
	}
	
	
}
