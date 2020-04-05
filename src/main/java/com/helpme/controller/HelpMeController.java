package com.helpme.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.helpme.config.HelpMeContants;
import com.helpme.model.LoginBean;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpListResponse;
import com.helpme.model.ResponseBean;
import com.helpme.model.UserBean;
import com.helpme.service.UserService;

@RestController
public class HelpMeController {

	@Autowired
	UserService userService;
	
	 private static final Logger logger = LogManager.getLogger(HelpMeController.class);
	
	@PostMapping("requestotp")
	@ResponseBody
	public ResponseBean sendOTP(@RequestBody LoginBean loginBean) {
		try {
			loginBean = userService.generateAndSaveOTP(loginBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception", e);
			return new ResponseBean(HelpMeContants.ERR_SERVER_ISSUE, HelpMeContants.MSG_SERVER_ISSUE);
		}
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	
	@PostMapping("login")
	@ResponseBody
	public ResponseBean login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
		logger.debug(loginBean.toString());
		if(userService.login(loginBean)) {
			logger.debug("Login Success");
//			HttpSession session = request.getSession(true);
//			session.setAttribute("loginBean", loginBean);
			return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
		} else {
			logger.debug("Login Failed");
			return new ResponseBean(HelpMeContants.ERR_LOGIN_FAILED, HelpMeContants.MSG_LOGIN_FAILED);
		}
	}
	

	

	/*-
	@GetMapping("userDetails")
	@ResponseBody
	public List<UserBean> userList() {
		return userService.userList();
	}
	
	*/
	
	@PostMapping("createHelp")
	@ResponseBody
	public ResponseBean createHelp(@RequestBody HelpBean helpBean, HttpServletRequest request) {
		logger.debug("User Detailes: " + helpBean.toString());
		helpBean = userService.createHelp(helpBean);
		logger.debug("User Detailes Saved: " + helpBean.toString());
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	
	@PostMapping("assignHelp/{helpItemId}/{userId}")
	@ResponseBody
	public ResponseBean assignHelp(@PathVariable int helpItemId, @PathVariable int userId, HttpServletRequest request) {
		logger.debug("helpItemId: " + helpItemId + " userId:" + userId);
		HelpBean helpBean = userService.assignHelp(helpItemId, userId);
		if(helpBean == null) {
			logger.debug("Assign Help Failed: NULL");
			return new ResponseBean(HelpMeContants.ERR_SERVER_ISSUE, HelpMeContants.MSG_SERVER_ISSUE);
		}
		logger.debug("Assign Help Saved: " + helpBean.toString());
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	
	@PostMapping("updatenHelp/{helpItemId}/{userId}/{itemStatus}")
	@ResponseBody
	public ResponseBean updateHelp(@PathVariable int helpItemId, @PathVariable int userId, @PathVariable String itemStatus, HttpServletRequest request) {
		logger.debug("helpItemId: " + helpItemId + " userId:" + userId);
		HelpBean helpBean = userService.updateHelp(helpItemId, userId, itemStatus);
		if(helpBean == null) {
			logger.debug("Assign Help Failed: NULL");
			return new ResponseBean(HelpMeContants.ERR_SERVER_ISSUE, HelpMeContants.MSG_SERVER_ISSUE);
		}
		logger.debug("Assign Help Saved: " + helpBean.toString());
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	
	@PostMapping("listhelp")
	@ResponseBody
	public ResponseBean getHelpList(@RequestBody UserBean userBean, HttpServletRequest request) {
		logger.debug("list Need: " + userBean.toString());
		HelpListResponse helpListResponse = userService.userHelpList(userBean.getId());
		helpListResponse.setErrCode(HelpMeContants.ERR_SUCCESS);
		helpListResponse.setErrMsg(HelpMeContants.MSG_SUCCESS);
		logger.debug("list Need response: " + helpListResponse.toString());
		return helpListResponse;
	}
}
