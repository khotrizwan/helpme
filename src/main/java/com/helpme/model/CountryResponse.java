package com.helpme.model;

import java.util.List;

public class CountryResponse extends ResponseBean
{
	private List<Country> country;

	public List<Country> getCountry() {
		return country;
	}

	public void setCountry(List<Country> country) {
		this.country = country;
	}


}
