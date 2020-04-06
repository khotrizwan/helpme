package com.helpme.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helpme.config.HelpMeContants;
import com.helpme.controller.HelpMeController;
import com.helpme.model.ResponseBean;

@ControllerAdvice
public class APIExceptionHandler {

	private static final Logger logger = LogManager.getLogger(HelpMeController.class);
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseBean handleException(Exception e) { {
		logger.error("Exception", e);
		return new ResponseBean(HelpMeContants.ERR_CODE_EXCEPTION, HelpMeContants.MSG_SERVER_ISSUE);
	}
		
	}
}
