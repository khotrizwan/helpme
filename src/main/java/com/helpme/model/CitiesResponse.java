package com.helpme.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CitiesResponse extends ResponseBean 
{
	@JsonProperty("state_id")
	private int stateId;
	
	private List<City> cityList;
	
	public CitiesResponse()
	{
		setCityList(new ArrayList<City>());
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
}
