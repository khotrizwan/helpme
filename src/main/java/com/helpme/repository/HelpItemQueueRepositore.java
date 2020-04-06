package com.helpme.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.helpme.model.HelpItemQueueBean;


public interface HelpItemQueueRepositore extends CrudRepository<HelpItemQueueBean, Integer> {
	List<HelpItemQueueBean> findByUserId(int userId);
	void deleteByHelpItemId(int helpItemId);
}
