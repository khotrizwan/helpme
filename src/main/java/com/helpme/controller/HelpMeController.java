package com.helpme.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.helpme.model.LoginBean;
import com.helpme.model.NeedBean;
import com.helpme.model.NeedMapResponse;
import com.helpme.model.ResponseBean;
import com.helpme.model.UserBean;
import com.helpme.service.UserService;
import com.helpme.util.HelpMeContants;

@Controller
public class HelpMeController {

	@Autowired
	UserService userService;
	
	 private static final Logger logger = LogManager.getLogger(HelpMeController.class);
	
	@PostMapping("sendotp")
	@ResponseBody
	public ResponseBean sendOTP(@RequestBody LoginBean login) {
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	
	@PostMapping("login")
	@ResponseBody
	public ResponseBean login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
		logger.debug(loginBean.toString());
		if(userService.login(loginBean)) {
			logger.debug("Login Success");
			HttpSession session = request.getSession(true);
			session.setAttribute("loginBean", loginBean);
			return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
		} else {
			logger.debug("Login Failed");
			return new ResponseBean(HelpMeContants.ERR_LOGIN_FAILED, HelpMeContants.MSG_LOGIN_FAILED);
		}
	}
	
	@PostMapping("userDetails")
	@ResponseBody
	public ResponseBean createUser(@RequestBody UserBean userBean, HttpServletRequest request) {
		logger.debug(userBean.toString());
		HttpSession session = request.getSession(false);
		if(session == null) {
			logger.debug("Session Null: " + userBean);
			return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
		}
		LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
		if(loginBean == null) {
			logger.debug("Not yet loggedIn: " + userBean);
			return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
		}
		userBean.setMobileno(loginBean.getMobileno());
		userBean = userService.saveUser(userBean);
		logger.debug("User Detailes Saved: " + userBean);
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	

	/*-
	@GetMapping("userDetails")
	@ResponseBody
	public List<UserBean> userList() {
		return userService.userList();
	}
	
	*/
	
	@PostMapping("need")
	@ResponseBody
	public ResponseBean addNeed(@RequestBody NeedBean needBean, HttpServletRequest request) {
		logger.debug(needBean.toString());
		HttpSession session = request.getSession(false);
		if(session == null) {
			logger.debug("Session Null: " + needBean);
			return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
		}
		LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
		if(loginBean == null) {
			logger.debug("Not yet loggedIn: " + needBean);
			return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
		}
		needBean.setMobileno(loginBean.getMobileno());
		needBean.setStatus(HelpMeContants.STATUS_PENDING);
		needBean = userService.saveNeed(needBean);
		logger.debug("User Detailes Saved: " + needBean);
		return new ResponseBean(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS);
	}
	
	@GetMapping("need")
	@ResponseBody
	public ResponseBean getNeedList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			logger.debug("Session Null: ");
			return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
		}
		LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");
		if(loginBean == null) {
			logger.debug("Not yet loggedIn: ");
			return new ResponseBean(HelpMeContants.ERR_INVALID_REQUEST, HelpMeContants.MSG_INVALID_REQUEST);
		}
		return (new NeedMapResponse(HelpMeContants.ERR_SUCCESS, HelpMeContants.MSG_SUCCESS,userService.userNeedsStatus(loginBean.getMobileno())));
	}
}
