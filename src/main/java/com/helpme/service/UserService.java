package com.helpme.service;

import java.util.List;
import java.util.Map;

import com.helpme.model.LoginBean;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpListResponse;
import com.helpme.model.OrgBean;
import com.helpme.model.UserBean;

public interface UserService {
	public UserBean login(LoginBean loginBean);
	public UserBean saveHelpFinder(UserBean userBean);
	public UserBean saveServiceProvider(OrgBean orgUserBean);
	
	public List<UserBean> userList();
	public HelpBean createHelp(HelpBean needBean);
	public List<HelpBean> userNeeds();
	public List<HelpBean> userNeeds(String mobileno);
	public Map<String, List<HelpBean>> userNeedsStatus();
	public HelpListResponse userHelpList(int userId, String helpItemStatus);
	public LoginBean generateAndSaveOTP(LoginBean loginBean) throws Exception;
	public HelpBean assignHelp(int helpItemId, int userId);
	public HelpBean updateHelp(int helpItemId, int userId, String itemStatus);
	public List<UserBean> listServiceProvider(int cityId, int categoryId);
	public UserBean saveVolunteer(UserBean userBean);
	public List<HelpBean> getOpenHelpItems();
	public Map<Integer, List<HelpBean>> getCityHelpMap(List<HelpBean> helpList);
	public List<UserBean> getVolunteerUsers(int cityId);
	public int getNextVolunteerId(List<UserBean> volunteers);
}
