package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.helpme.model.HelpBean;

public interface HelpRepository  extends CrudRepository<HelpBean, Integer>{
	public List<HelpBean> findByMobileno(String mobileno);
	public List<HelpBean> findByUserId(int userId);
	public List<HelpBean> findByAssignedUserId(int userId);
	public List<HelpBean> findByVolunteerUserId(int userId);
	
}
