package com.helpme.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpme.model.LoginBean;
import com.helpme.model.NeedBean;
import com.helpme.model.UserBean;
import com.helpme.repository.LoginRepository;
import com.helpme.repository.NeedRepository;
import com.helpme.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	LoginRepository login;
	
	@Autowired
	UserRepository user;
	
	@Autowired
	NeedRepository need;
	
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
				typeNeeds = map.get(needBean.getType());
			}
			typeNeeds.add(needBean);
			map.put(key, typeNeeds);
		}
		return map;
	}

	@Override
	public List<NeedBean> userNeeds(String mobileno) {
		return need.findByMobileno(mobileno);
	}

	@Override
	public Map<String, List<NeedBean>> userNeedsStatus(String mobileno) {
		return getNeedsMap(need.findByMobileno(mobileno));
	}

}
