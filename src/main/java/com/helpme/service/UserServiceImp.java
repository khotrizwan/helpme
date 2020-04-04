package com.helpme.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helpme.config.HelpMeContants;
import com.helpme.model.LoginBean;
import com.helpme.model.NeedBean;
import com.helpme.model.NeedListResponse;
import com.helpme.model.UserBean;
import com.helpme.repository.LoginRepository;
import com.helpme.repository.NeedRepository;
import com.helpme.repository.UserRepository;
import com.helpme.util.HelpMeUtil;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	LoginRepository login;

	@Autowired
	UserRepository user;

	@Autowired
	NeedRepository need;
	
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
	public UserBean saveUser(UserBean userBean) {		
		return user.save(userBean);
	}

	@Override
	public List<UserBean> userList() {
		return (List<UserBean>) user.findAll();
	}

	@Override
	public NeedBean saveNeed(NeedBean needBean) {
		return need.save(needBean);
	}

	@Override
	public List<NeedBean> userNeeds() {
		return (List<NeedBean>) need.findAll();
	}

	@Override
	public Map<String, List<NeedBean>> userNeedsStatus() {
		return getNeedsMap( (List<NeedBean>) need.findAll());
	}

	private Map<String, List<NeedBean>> getNeedsMap(List<NeedBean> needs) {
		Map<String, List<NeedBean>> map = new HashMap<String, List<NeedBean>>();
		for (NeedBean needBean : needs) {
			List<NeedBean> typeNeeds = new ArrayList<NeedBean>();
			String key = needBean.getStatus();
			if(map.containsKey(key)) {
				typeNeeds = map.get(key);
			}
			typeNeeds.add(needBean);
			map.put(key, typeNeeds);
		}
		return map;
	}

	private NeedListResponse getNeedsList(List<NeedBean> needs) {
		List<NeedBean> pending = new ArrayList<NeedBean>();
		List<NeedBean> accepted = new ArrayList<NeedBean>();
		List<NeedBean> complete = new ArrayList<NeedBean>();

		for (NeedBean needBean : needs) {
			String key = needBean.getStatus();
			if(key.equalsIgnoreCase(HelpMeContants.STATUS_PENDING)) {
				pending.add(needBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_ACCEPTED)) {
				accepted.add(needBean);
			} else if(key.equalsIgnoreCase(HelpMeContants.STATUS_COMPLETE)) {
				complete.add(needBean);
			}
		}
		
		NeedListResponse needListResponse = new NeedListResponse();
		needListResponse.setAccepted(accepted);
		needListResponse.setComplete(complete);
		needListResponse.setPending(pending);
		return needListResponse;
	}

	@Override
	public List<NeedBean> userNeeds(String mobileno) {
		return need.findByMobileno(mobileno);
	}

	@Override
	public NeedListResponse userNeedsStatus(String mobileno) {
		return getNeedsList(need.findByMobileno(mobileno));
	}

	@Override
	public LoginBean generateAndSaveOTP(LoginBean loginBean) throws Exception {
		loginBean.setOtp(HelpMeUtil.generateOTP());
		loginBean = login.save(loginBean);
		HelpMeUtil.sendOTP(loginBean, env.getProperty(HelpMeContants.COMPANY_NAME), env.getProperty(HelpMeContants.PARAM3), env.getProperty(HelpMeContants.AUTHKEY), env.getProperty(HelpMeContants.SMS_URL), env.getProperty(HelpMeContants.TEMPLATE_ID));
		return loginBean;
	}

}
