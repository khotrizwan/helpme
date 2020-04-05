package com.helpme.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helpme.config.HelpMeContants;
import com.helpme.model.OrgBean;
import com.helpme.model.ResponseBean;
import com.helpme.model.UserBean;
import com.helpme.model.UserResponse;
import com.helpme.service.UserService;

@RestController
public class CreateEntityController {

	@Autowired
	UserService userService;
	
	 private static final Logger logger = LogManager.getLogger(CreateEntityController.class);
	 
		@PostMapping("createHelpFinder")
		@ResponseBody
		public ResponseBean createUser(@RequestBody UserBean userBean, HttpServletRequest request) {
			logger.debug(userBean.toString());
//			HttpSession session = request.getSession(false);
//			if(session == null) {
//				logger.debug("Session Null: " + userBean);
//				return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
//			}
//			LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
//			if(loginBean == null) {
//				logger.debug("Not yet loggedIn: " + userBean);
//				return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
//			}
//			userBean.setMobileno(loginBean.getMobileno());
			userBean = userService.saveHelpFinder(userBean);
			logger.debug("User Detailes Saved: " + userBean);
			UserResponse response = new UserResponse();
			response.setErrCode(HelpMeContants.ERR_SUCCESS);
			response.setErrMsg(HelpMeContants.MSG_SUCCESS);
			response.setUserBean(userBean);
			return response;
		}
		
		
		@PostMapping("createServiceProvider")
		@ResponseBody
		public ResponseBean createServiceProvider(@RequestBody OrgBean orgBean) {
			logger.debug(orgBean.toString());

			UserBean userBean = userService.saveServiceProvider(orgBean);
			logger.debug("Organization Details Saved: " + orgBean);
			UserResponse response = new UserResponse();
			response.setErrCode(HelpMeContants.ERR_SUCCESS);
			response.setErrMsg(HelpMeContants.MSG_SUCCESS);
			response.setUserBean(userBean);
			return response;
		}
	 
	 
}
