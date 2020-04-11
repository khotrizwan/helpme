package com.helpme.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.helpme.config.HelpMeContants;
import com.helpme.controller.HelpMeController;
import com.helpme.model.ResponseBean;
import com.helpme.model.UserBean;
import com.helpme.service.UserService;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpItemQueueBean;


@Component
public class VolunteerAssignment {

	@Autowired
	UserService userService;
	
    private static final Logger logger = LogManager.getLogger(VolunteerAssignment.class);

	
	@Scheduled(fixedRate=60000)
	public void task_run(){
		System.out.println("Welcome to Task Scheduling: " + new java.util.Date());
		Map<Integer, List<HelpBean>> cityHelpMap = new HashMap<Integer, List<HelpBean>>();
		//get all open items created more than 4hrs ago
		//assign them to volunteers for that city 
		//get helpitem's city via userid and group open helpitems by city
		//fetch volunteers by cityId and assign above 
		logger.debug("------ task_run ------------- ");
		try {
			List<HelpBean> openHelpItems = userService.getOpenHelpItems();
			
			logger.debug("Open Help Items size: " + openHelpItems.size());
			if(openHelpItems != null && openHelpItems.size() > 0) {
				for (HelpBean helpItem : openHelpItems) {
					System.out.println(helpItem.getId() + " | " + helpItem.getUserId() );
				}
				
				cityHelpMap = userService.getCityHelpMap(openHelpItems);
			
				cityHelpMap.forEach((k,v) -> 
				{
					System.out.println("Key = " + k + ", Value = " + v.size());
					List<UserBean> cityVolunteers =  userService.getVolunteerUsers(k);
					System.out.println("Volunteers: " + cityVolunteers);
					logger.debug("cityId: " + k + " helpItems size:" + v.size() + " No. of volunteers: " + cityVolunteers.size());
					if(cityVolunteers.size()>0) {
						for (HelpBean helpBean : v) {
							logger.debug("Help Item: " + helpBean.getId());
							//for (UserBean volunteerUser : cityVolunteers) {
							int nextVolunteerId = userService.getNextVolunteerId(cityVolunteers);
							logger.debug("Vounteer User ID: " + nextVolunteerId);
							
							HelpBean assignedHelpBean = userService.assignHelp(helpBean.getId(), nextVolunteerId);
							if(assignedHelpBean == null) {
								logger.debug("Assign Help Failed: NULL");
							}
							logger.debug("Assign Help Saved: " + assignedHelpBean.toString());
							//}
						}
					}else {
						logger.debug("No Volunteers for this cityId: " + k);
					}
					
				}	
				);
			 
			}else {
				logger.debug("No open Help Items to be assigned to volunteers!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception", e);
		}
		
	}
}
