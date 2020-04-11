package com.helpme.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.helpme.config.HelpMeContants;
import com.helpme.model.HelpBean;
import com.helpme.model.UserBean;

public interface HelpRepository  extends CrudRepository<HelpBean, Integer>{
	//public List<HelpBean> findByMobileno(String mobileno);
	public List<HelpBean> findByUserId(int userId);
	public List<HelpBean> findByAssignedUserId(int userId);
	public List<HelpBean> findByVolunteerUserId(int userId);
	public List<HelpBean> findByHelpItemStatus(String helpItemStatus);
	
	@Query(value = "SELECT h.* FROM help_item h WHERE h.help_item_status='OPEN' and h.volunteer_user_id =0 ", nativeQuery = true)//and h.create_date < DATE_SUB(NOW(), INTERVAL 1 MINUTE)
	//Optional<List<UserBean>> getServiceProviders();
	Optional<List<HelpBean>> getOpenHelpItems();
	
	@Query(value = "SELECT count(0) FROM help_item h WHERE h.help_item_status = 'OPEN' and h.volunteer_user_id = :userId", nativeQuery = true)
	int getVolunteerAssignedHelpCount(int userId);
}
