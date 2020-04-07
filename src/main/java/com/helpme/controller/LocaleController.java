package com.helpme.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger logger = LogManager.getLogger(LocaleController.class);
	@Autowired
	LocaleService localeService;
	
	@GetMapping(value="/countries",produces = "application/json")
	@ResponseBody
	ResponseBean getCountries()
	{
		logger.debug("------ countries ------------- ");
		return localeService.getCountries();
	}
	
	@GetMapping(value="/states")
	@ResponseBody
	ResponseBean getStates(@RequestParam(name="countryId") @NotNull String countryId)
	{
		logger.debug("------ states ------------- ");
		return localeService.getStates(countryId);
	}
	
	@GetMapping(value="/cities")
	@ResponseBody
	ResponseBean getCities(@RequestParam(name="stateId") @NotNull String stateId)
	{
		logger.debug("------ cities ------------- ");
		return localeService.getCities(stateId);
	}

}
