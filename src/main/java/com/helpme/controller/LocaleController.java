package com.helpme.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helpme.model.ResponseBean;
import com.helpme.service.LocaleService;
import com.sun.istack.NotNull;

@RestController
public class LocaleController 
{
	@Autowired
	LocaleService localeService;
	
	@GetMapping(value="/countries",produces = "application/json")
	@ResponseBody
	ResponseBean getCountries(HttpServletResponse response)
	{
		return localeService.getCountries();
	}
	
	@GetMapping(value="/states")
	@ResponseBody
	ResponseBean getStates(@RequestParam(name="countryId") @NotNull String countryId)
	{
		return localeService.getStates(countryId);
	}
	
	@GetMapping(value="/cities")
	@ResponseBody
	ResponseBean getCities(@RequestParam(name="stateId") @NotNull String stateId)
	{
		return localeService.getCities(stateId);
	}

}
