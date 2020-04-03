package com.helpme.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.helpme.model.LoginBean;
import com.helpme.model.NeedBean;
import com.helpme.model.UserBean;

public interface UserService {
	public boolean login(LoginBean loginBean);
	public UserBean saveUser(UserBean userBean);
	public List<UserBean> userList();
	public NeedBean saveNeed(NeedBean needBean);
	public List<NeedBean> userNeeds();
	public List<NeedBean> userNeeds(String mobileno);
	public Map<String, List<NeedBean>> userNeedsStatus();
	public Map<String, List<NeedBean>> userNeedsStatus(String mobileno);
}
