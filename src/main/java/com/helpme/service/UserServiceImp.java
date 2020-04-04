package com.helpme.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.helpme.config.HelpMeContants;
import com.helpme.model.LoginBean;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpListResponse;
import com.helpme.model.OrgBean;
import com.helpme.model.UserBean;
import com.helpme.repository.LoginRepository;
import com.helpme.repository.HelpRepository;
import com.helpme.repository.OrgRepository;
import com.helpme.repository.UserRepository;
import com.helpme.util.HelpMeUtil;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	LoginRepository login;

	@Autowired
	UserRepository user;
	
	@Autowired
	OrgRepository org;

	@Autowired
	HelpRepository help;

	@Autowired
	private Environment env;

	@Override
	public boolean login(LoginBean loginBean) {
		Optional<LoginBean> bdDetails = login.findById(loginBean.getMobileno());
		if(bdDetails.isPresent() && bdDetails.get().getOtp().equals(loginBean.getOtp())) 
			return true;

		return false;
	}

	@Override
	public UserBean saveHelpFinder(UserBean userBean) {	
		userBean.setIsAdmin(HelpMeContants.Y);
		userBean.setUserType(HelpMeContants.USER_TYPE_HELPFINDER); //help_finder / service provider / volunteer / HelpMePlease 
		userBean.setOrganizationId(1);
		userBean.setIsActive(HelpMeContants.Y);
		userBean.setCreateDate(new Date());
		return user.save(userBean);
	}
	
	@Override
	public OrgBean saveServiceProvider(OrgBean orgBean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private OrgBean saveOrganization(OrgBean orgBean) {	
		orgBean.setOrgType(HelpMeContants.ORG_TYPE_SERVICE_PROVIDER);; //service_provider / Volunteer / HelpmePlease / HelpFinder
		orgBean.setIsIndividual(HelpMeContants.N);
		orgBean.setCanAccept(HelpMeContants.N);
		orgBean.setIsActive(HelpMeContants.Y);
		orgBean.setCreateDate(new Date());
		return org.save(orgBean);
	}
	
	private UserBean saveServiceProviderUser(UserBean userBean) {	
		userBean.setIsAdmin(HelpMeContants.Y);
		userBean.setUserType(HelpMeContants.USER_TYPE_SERVICE_PROVIDER); //help_finder / service provider / volunteer / HelpMePlease 
		userBean.setOrganizationId(1);
		userBean.setIsActive(HelpMeContants.Y);
		userBean.setCreateDate(new Date());
		return user.save(userBean);
	}
	

	@Override
	public List<UserBean> userList() {
		return (List<UserBean>) user.findAll();
	}

	@Override
	public HelpBean createHelp(HelpBean helpBean) {
		helpBean.setHelpItemStatus(HelpMeContants.STATUS_OPEN);
		return help.save(helpBean);
	}

	@Override
	public List<HelpBean> userNeeds() {
		return (List<HelpBean>) help.findAll();
	}

	@Override
	public Map<String, List<HelpBean>> userNeedsStatus() {
		return getNeedsMap( (List<HelpBean>) help.findAll());
	}

	private Map<String, List<HelpBean>> getNeedsMap(List<HelpBean> needs) {
		Map<String, List<HelpBean>> map = new HashMap<String, List<HelpBean>>();
		for (HelpBean needBean : needs) {
			List<HelpBean> typeNeeds = new ArrayList<HelpBean>();
			String key = needBean.getHelpItemStatus();
			if(map.containsKey(key)) {
				typeNeeds = map.get(key);
			}
			typeNeeds.add(needBean);
			map.put(key, typeNeeds);
		}
		return map;
	}

	private HelpListResponse getNeedsList(List<HelpBean> helps) {
		List<HelpBean> pending = new ArrayList<HelpBean>();
		List<HelpBean> accepted = new ArrayList<HelpBean>();
		List<HelpBean> close = new ArrayList<HelpBean>();
		List<HelpBean> rejected = new ArrayList<HelpBean>();
		List<HelpBean> resolved = new ArrayList<HelpBean>();
		List<HelpBean> unresolved = new ArrayList<HelpBean>();

		for (HelpBean helpBean : helps) {
			String key = helpBean.getHelpItemStatus();
			if(key.equalsIgnoreCase(HelpMeContants.STATUS_OPEN)) {
				pending.add(helpBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_ACCEPTED)) {
				accepted.add(helpBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_CLOSED)) {
				close.add(helpBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_REJECTED)) {
				rejected.add(helpBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_RESOLVED)) {
				resolved.add(helpBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_UN_RESOLVED)) {
				unresolved.add(helpBean);
			}
		}

		HelpListResponse helpListResponse = new HelpListResponse();
		helpListResponse.setAccepted(accepted);
		helpListResponse.setClose(close);
		helpListResponse.setPending(pending);
		helpListResponse.setRejected(rejected);
		helpListResponse.setResolved(resolved);
		helpListResponse.setUnresolved(unresolved);
		return helpListResponse;
	}

	@Override
	public List<HelpBean> userNeeds(String mobileno) {
		return null;//help.findByMobileno(mobileno);
	}

	@Override
	public HelpListResponse userHelpList(int userId) {
		Optional<UserBean> userBean = user.findById(userId);
		if(userBean.isPresent()) { 
			if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_HELPFINDER)) {
				return getNeedsList(help.findByUserId(userId));
			} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_SERVICE_PROVIDER)) { 
				return getNeedsList(help.findByAssignedUserId(userId));
			} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_VOLUNTEER)) {
				return getNeedsList(help.findByVolunteerUserId(userId));
			}
		}
		return null;
	}

	@Override
	public LoginBean generateAndSaveOTP(LoginBean loginBean) throws Exception {
		loginBean.setOtp(HelpMeUtil.generateOTP());
		loginBean = login.save(loginBean);
		HelpMeUtil.sendOTP(loginBean, env.getProperty(HelpMeContants.COMPANY_NAME), env.getProperty(HelpMeContants.PARAM3), env.getProperty(HelpMeContants.AUTHKEY), env.getProperty(HelpMeContants.SMS_URL), env.getProperty(HelpMeContants.TEMPLATE_ID));
		return loginBean;
	}

	

}
