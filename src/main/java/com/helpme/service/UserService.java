package com.helpme.service;

import java.util.List;
import java.util.Map;

import com.helpme.model.LoginBean;
import com.helpme.model.NeedBean;
import com.helpme.model.NeedListResponse;
import com.helpme.model.OrgBean;
import com.helpme.model.UserBean;

public interface UserService {
	public boolean login(LoginBean loginBean);
	public UserBean saveHelpFinder(UserBean userBean);
	public OrgBean saveServiceProvider(OrgBean orgUserBean);
	
	public List<UserBean> userList();
	public NeedBean saveNeed(NeedBean needBean);
	public List<NeedBean> userNeeds();
	public List<NeedBean> userNeeds(String mobileno);
	public Map<String, List<NeedBean>> userNeedsStatus();
	public NeedListResponse userNeedsStatus(String mobileno);
	public LoginBean generateAndSaveOTP(LoginBean loginBean) throws Exception;
}
