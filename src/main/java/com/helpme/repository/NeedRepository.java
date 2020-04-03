package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.helpme.model.NeedBean;

public interface NeedRepository  extends CrudRepository<NeedBean, Integer>{
	public List<NeedBean> findByMobileno(String mobileno);
	
}
