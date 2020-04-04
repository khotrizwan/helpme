package com.helpme.service;

import java.util.List;
import java.util.Map;

import com.helpme.model.LoginBean;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpListResponse;
import com.helpme.model.OrgBean;
import com.helpme.model.UserBean;

public interface UserService {
	public boolean login(LoginBean loginBean);
	public UserBean saveHelpFinder(UserBean userBean);
	public OrgBean saveServiceProvider(OrgBean orgUserBean);
	
	public List<UserBean> userList();
	public HelpBean createHelp(HelpBean needBean);
	public List<HelpBean> userNeeds();
	public List<HelpBean> userNeeds(String mobileno);
	public Map<String, List<HelpBean>> userNeedsStatus();
	public HelpListResponse userHelpList(int userId);
	public LoginBean generateAndSaveOTP(LoginBean loginBean) throws Exception;
}
