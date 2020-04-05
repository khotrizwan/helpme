package com.helpme.service;

import com.helpme.model.ResponseBean;

public interface LocaleService 
{
	ResponseBean getCountries();
	
	ResponseBean getStates(String countryId);
	
	ResponseBean getCities(String stateId);
	
}
