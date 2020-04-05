package com.helpme.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpme.model.CitiesResponse;
import com.helpme.model.City;
import com.helpme.model.Country;
import com.helpme.model.CountryResponse;
import com.helpme.model.ResponseBean;
import com.helpme.model.State;
import com.helpme.model.StatesResponse;
import com.helpme.repository.CityRepository;
import com.helpme.repository.CountryRepository;
import com.helpme.repository.StateRepository;

@Service
public class LocaleServiceImpl implements LocaleService 
{
	@Autowired
	CountryRepository country;
	
	@Autowired
	StateRepository state;	
	
	@Autowired
	CityRepository city;
	
	@Override
	public ResponseBean getCountries() 
	{
		List<Country> countries =  (List<Country>) country.findAll();
		CountryResponse data = new CountryResponse();
		data.setCountry(countries);
		return data;
	}

	@Override
	public ResponseBean getStates(String countryId) 
	{
		List<String> filter = new ArrayList<String>();
		filter.add(countryId);
		
		List<State> stateList =  (List<State>) state.findAllById(filter);
		StatesResponse data = new StatesResponse();
		data.setStateList(stateList);
		return data;
		
	}

	@Override
	public ResponseBean getCities(String stateId) 
	{
		List<String> filter = new ArrayList<String>();
		filter.add(stateId);
		
		List<City> cityList =  (List<City>) city.findAllById(filter);
		CitiesResponse data = new CitiesResponse();
		data.setCityList(cityList);
		return data;
	}

}
