package com.helpme.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.helpme.config.HelpMeContants;
import com.helpme.controller.HelpMeController;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpHistoryBean;
import com.helpme.model.HelpItemQueueBean;
import com.helpme.model.HelpListResponse;
import com.helpme.model.HelpResponseBean;
import com.helpme.model.LoginBean;
import com.helpme.model.OrgBean;
import com.helpme.model.OrgHelpCategory;
import com.helpme.model.UserBean;
import com.helpme.repository.HelpHistoryRepository;
import com.helpme.repository.HelpItemQueueRepositore;
import com.helpme.repository.HelpRepository;
import com.helpme.repository.LoginRepository;
import com.helpme.repository.OrgHelpCategoryRepo;
import com.helpme.repository.OrgRepository;
import com.helpme.repository.UserRepository;
import com.helpme.util.HelpMeUtil;

@Service
public class UserServiceImp implements UserService{

	private static final Logger logger = LogManager.getLogger(UserServiceImp.class);

	@Autowired
	LoginRepository login;

	@Autowired
	UserRepository user;

	@Autowired
	OrgRepository org;

	@Autowired
	OrgHelpCategoryRepo orgHelpCat;

	@Autowired
	HelpRepository help;

	@Autowired
	HelpHistoryRepository helpHistory;

	@Autowired
	HelpItemQueueRepositore helpItemQueue;

	@Autowired
	private Environment env;

	@Override
	public UserBean login(LoginBean loginBean) {
		Optional<LoginBean> bdDetails = login.findById(loginBean.getMobileno());
		if(bdDetails.isPresent() && bdDetails.get().getOtp().equals(loginBean.getOtp())) {
			Optional<UserBean> userOptional = user.findByMobileno(loginBean.getMobileno());
			if(userOptional.isPresent())
				return userOptional.get();
		}

		return null;
	}


	@Override
	public UserBean saveHelpFinder(UserBean userBean) {	
		return createUser(userBean, 1, HelpMeContants.USER_TYPE_HELPFINDER, HelpMeContants.Y);
	}

	@Override
	public UserBean saveVolunteer(UserBean userBean) {	
		return createUser(userBean, 2, HelpMeContants.USER_TYPE_VOLUNTEER, HelpMeContants.N);
	}

	private UserBean createUser(UserBean userBean, int organizationId, String userType, String isAdmin) {
		Optional<LoginBean> bdDetails = login.findById(userBean.getMobileno());
		if(bdDetails.isPresent() && bdDetails.get().getOtp().equals(userBean.getOtp())) {
			userBean.setIsAdmin(isAdmin);
			userBean.setUserType(userType); //help_finder / service provider / volunteer / HelpMePlease 
			userBean.setOrganizationId(organizationId);
			userBean.setIsActive(HelpMeContants.Y);
			userBean.setCreateDate(new Date());
			logger.debug("Before Save User " + userBean);
			userBean = user.save(userBean);
			logger.debug("After Save User " + userBean);
			return userBean; 
		} else {
			logger.debug("Invalid OTP");
		}
		return null;
	}


	/*
	 * "mobileno":"8169275469", "firstName":"Parth", "middleName":"",
	 * "lastName":"Nemana", "emailAddress":"parth8nemana@gmail.com",
	 * "latitude":"1234", "longitude":"5678", "address":"abcd", "cityId":10
	 */

	@Override
	@Transactional
	public UserBean saveServiceProvider(OrgBean orgBean) {
		Optional<LoginBean> bdDetails = login.findById(orgBean.getMobileno());
		if(bdDetails.isPresent() && bdDetails.get().getOtp().equals(orgBean.getOtp())) {
			// TODO Auto-generated method stub
			orgBean = saveOrganization(orgBean);
			saveOrgHelpCategory(orgBean);
			logger.debug(orgBean.getId());

			UserBean userBean = new UserBean();
			userBean.setMobileno(orgBean.getMobileno());
			userBean.setFirstName(orgBean.getFirstName());
			userBean.setMiddleName(orgBean.getMiddleName());
			userBean.setLastName(orgBean.getLastName());
			userBean.setEmailAddress(orgBean.getEmailAddress());
			userBean.setLatitude(orgBean.getLatitude());
			userBean.setLongitude(orgBean.getLongitude());
			userBean.setAddress(orgBean.getAddress());
			userBean.setCityId(orgBean.getCityId());
			userBean.setOrganizationId(orgBean.getId());
			logger.debug("In  saveServiceProvider Org ID: " + orgBean.getId());
			saveServiceProviderUser(userBean);	
			logger.debug("In  saveServiceProvider User ID: "+ userBean.getId());

			return userBean;
		} else {
			logger.debug("Invalid OTP");
		}
		return null;

	}

	private void saveOrgHelpCategory(OrgBean orgBean) {
		String[] catIds = orgBean.getCatIds().split(HelpMeContants.CAT_SEPARATOR);
		List<OrgHelpCategory> orgHelpCategories = new ArrayList<OrgHelpCategory>();
		for(String catId : catIds) {
			OrgHelpCategory orgHelpCategory = new OrgHelpCategory();
			orgHelpCategory.setOrganizationId(orgBean.getId());
			orgHelpCategory.setHelpCategoryId(Integer.parseInt(catId.trim()));
			orgHelpCategory.setIsActive(HelpMeContants.Y);
			orgHelpCategory.setCreateDate(new Date());
			orgHelpCategories.add(orgHelpCategory);
			logger.debug(orgHelpCategory.toString());
		}
		if(orgHelpCategories.size() > 0)
			orgHelpCat.saveAll(orgHelpCategories);
	}

	private OrgBean saveOrganization(OrgBean orgBean) {	
		orgBean.setOrgType(HelpMeContants.ORG_TYPE_SERVICE_PROVIDER);; //service_provider / Volunteer / HelpmePlease / HelpFinder
		orgBean.setIsActive(HelpMeContants.Y);
		orgBean.setCreateDate(new Date());
		return org.save(orgBean);
	}

	private UserBean saveServiceProviderUser(UserBean userBean) {	
		userBean.setIsAdmin(HelpMeContants.Y);
		userBean.setUserType(HelpMeContants.USER_TYPE_SERVICE_PROVIDER); //help_finder / service provider / volunteer / HelpMePlease 
		userBean.setIsActive(HelpMeContants.Y);
		userBean.setCreateDate(new Date());
		return user.save(userBean);
	}


	@Override
	public List<UserBean> userList() {
		return (List<UserBean>) user.findAll();
	}

	/**
	 * - Create Help Entry for User
	 * - Based on userId fetch cityId of Users
	 * - Find all service providers in the city who are serving user selected Help Category
	 * - if only one SP  :: assign to this SP
	 * - if less than 10
	 * 	  - Assign into Help Category user Queue
	 * - If more than 10
	 * 	  - Get Latitude,Longitude of the user from  userTable
	 *    - Find all the SP's serving selected Help Category within 3 KM range ( Use Algo )
	 *    - Map these SP's to Help Category user Queue
	 * 
	 * @param helpBean
	 * @return
	 */
	@Override
	public HelpBean createHelp(HelpBean helpBean) 
	{
		helpBean.setHelpItemStatus(HelpMeContants.STATUS_OPEN);
		helpBean.setCreateDate(new Date());
		helpBean = help.save(helpBean);

		Optional<UserBean> requestSeeker = user.findById(helpBean.getUserId());
		if(requestSeeker.isPresent()) 
		{
			UserBean requestSeekerBean = requestSeeker.get();
			List<OrgBean> spsToSeekHelp = new ArrayList<OrgBean>();

			// Getting all the service providers 
			//TODO :: Appply and Query
			//			List<OrgBean> serviceProviders = org.findByOrgType(HelpMeContants.USER_TYPE_SERVICE_PROVIDER);
			List<OrgBean> serviceProviders = new ArrayList<OrgBean>();
			List<OrgHelpCategory> serviceProvidersForCat = orgHelpCat.findByHelpCategoryId(helpBean.getHelpCategoryId());

			for(OrgHelpCategory serviceProviderCat:serviceProvidersForCat)
			{
				Optional<OrgBean> serviceProvider =   org.findById(serviceProviderCat.getOrganizationId());
				if(serviceProvider.isPresent())
				{
					serviceProviders.add(serviceProvider.get());
				}
			}
			logger.debug(" City Check start: " + helpBean);
			// Same City
			for(OrgBean serviceProvider:serviceProviders)
			{
				// Checking SP's are of Seeker's city
				if(serviceProvider.getCityId() == requestSeekerBean.getCityId())
				{
					spsToSeekHelp.add(serviceProvider);
				}
			}
			logger.debug(" Size check start: " + helpBean);
			// Checking size
//			if(spsToSeekHelp.size() == 1)
//			{
//				logger.debug(" Only One Org: " + helpBean);
//				UserBean user = getUserByOrganisation(spsToSeekHelp.get(0));
//				if(user != null) {
//					helpBean.setAssignedUserId(user.getId());
//					helpBean.setHelpItemStatus(HelpMeContants.STATUS_ACCEPTED);
//					helpBean = help.save(helpBean);
//				}
//				return helpBean;
//			}
			
			

			if (spsToSeekHelp.size() < 10)
			{
				logger.debug(" less than 10 orgs: " + helpBean);
				insertIntoQueue(helpBean, spsToSeekHelp);
				return helpBean;
			}

			logger.debug(" lat lang logic start: " + helpBean);
			// Lat Long Logic
			for (int i=0;i<spsToSeekHelp.size();i++)	
			{
				OrgBean serviceProvider = spsToSeekHelp.get(i);
				double distance = HelpMeUtil.getDistance(Long.valueOf(requestSeekerBean.getLatitude()), Long.valueOf(requestSeekerBean.getLongitude()), 
						Long.valueOf(serviceProvider.getLatitude()),   Long.valueOf(serviceProvider.getLongitude()));

				if(distance > 3)
				{
					spsToSeekHelp.remove(i);
					continue;
				}
			}

			if( spsToSeekHelp.size() != 0 )
			{
				logger.debug(" orgs founds with lat lang logic: " + helpBean);
				// Assigning to all service providers within 3 km range
				insertIntoQueue(helpBean, spsToSeekHelp);
				return helpBean;
			}

			logger.debug("all check failed or not orgs in neaby area: " + helpBean);
			// In worst case assign to all service providers for category selected
			insertIntoQueue(helpBean, serviceProviders);
			return helpBean;

		}

		return helpBean;
	}

	private UserBean getUserByOrganisation(OrgBean orgBean) {
		Optional<List<UserBean>> users = user.findByOrganizationId(orgBean.getId());
		if(users.isPresent()) {
			UserBean user = users.get().get(0);

			for(UserBean usr : users.get()) {
				if(usr.getIsAdmin().equals(HelpMeContants.Y)) {
					user = usr;
					break;
				}
			}

			return user;
		}
		return null;
	}


	private void insertIntoQueue(HelpBean helpBean, List<OrgBean> serviceProviders)
	{
		logger.debug(" insertIntoQueue start: " + helpBean);
		for(OrgBean serviceProvider:serviceProviders)
		{
			UserBean user = getUserByOrganisation(serviceProvider);
			if(user != null) {
				HelpItemQueueBean bean = new HelpItemQueueBean();
				bean.setHelpItemId(helpBean.getId());
				bean.setUserId(user.getId());
				helpItemQueue.save(bean);
			}
		}
		logger.debug(" insertIntoQueue End: " + helpBean);
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

	private HelpListResponse getHelpItemLists(List<HelpBean> helps, String helpItemStatus) {
		List<HelpResponseBean> pending = new ArrayList<HelpResponseBean>();
		List<HelpResponseBean> accepted = new ArrayList<HelpResponseBean>();
		List<HelpResponseBean> close = new ArrayList<HelpResponseBean>();
		List<HelpResponseBean> rejected = new ArrayList<HelpResponseBean>();
		List<HelpResponseBean> resolved = new ArrayList<HelpResponseBean>();
		List<HelpResponseBean> unresolved = new ArrayList<HelpResponseBean>();

		for (HelpBean helpBean : helps) {
			String key = helpBean.getHelpItemStatus();
			HelpResponseBean helpResponseBean = new HelpResponseBean(helpBean);
			Optional<UserBean> finderOptional = user.findById(helpResponseBean.getUserId());
			UserBean helpFinder= (finderOptional.isPresent() ? finderOptional.get() : null);
			UserBean orgUser = null;
			OrgBean organisation = null;
			if(helpResponseBean.getAssignedUserId() > 0) {
				Optional<UserBean> orgUserOptional = user.findById(helpResponseBean.getAssignedUserId());
				orgUser= (orgUserOptional.isPresent() ? orgUserOptional.get() : null);

				Optional<OrgBean> orgOptional = org.findById(orgUser.getOrganizationId());
				organisation= (orgOptional.isPresent() ? orgOptional.get() : null);
			}
			UserBean volunteer = null;
			if(helpResponseBean.getVolunteerUserId() > 0) {
				Optional<UserBean> volunteerUserOptional = user.findById(helpResponseBean.getVolunteerUserId());
				volunteer= (volunteerUserOptional.isPresent() ? volunteerUserOptional.get() : null);

			}

			helpResponseBean.setHelpFinder(helpFinder);
			helpResponseBean.setOrgUser(orgUser);
			helpResponseBean.setOrganisation(organisation);
			helpResponseBean.setVolunteer(volunteer);

			if((helpItemStatus==null || helpItemStatus.trim().length() == 0 || helpItemStatus.equals(HelpMeContants.STATUS_OPEN)) && key.equalsIgnoreCase(HelpMeContants.STATUS_OPEN)) {
				pending.add(helpResponseBean);
			} else if((helpItemStatus==null || helpItemStatus.trim().length() == 0 || helpItemStatus.equals(HelpMeContants.STATUS_ACCEPTED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_ACCEPTED)) {
				accepted.add(helpResponseBean);
			} else if((helpItemStatus==null || helpItemStatus.trim().length() == 0 || helpItemStatus.equals(HelpMeContants.STATUS_CLOSED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_CLOSED)) {
				close.add(helpResponseBean);
			} else if((helpItemStatus==null || helpItemStatus.trim().length() == 0 || helpItemStatus.equals(HelpMeContants.STATUS_REJECTED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_REJECTED)) {
				rejected.add(helpResponseBean);
			} else if((helpItemStatus==null || helpItemStatus.trim().length() == 0 || helpItemStatus.equals(HelpMeContants.STATUS_RESOLVED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_RESOLVED)) {
				resolved.add(helpResponseBean);
			} else if((helpItemStatus==null || helpItemStatus.trim().length() == 0 || helpItemStatus.equals(HelpMeContants.STATUS_UN_RESOLVED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_UN_RESOLVED)) {
				unresolved.add(helpResponseBean);
			}
		}

		HelpListResponse helpListResponse = new HelpListResponse();
		helpListResponse.setAccepted(accepted);
		helpListResponse.setClose(close);
		helpListResponse.setOpen(pending);
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
	public HelpListResponse userHelpList(int userId, String helpItemStatus) {
		if(helpItemStatus != null && helpItemStatus.equals(HelpMeContants.STATUS_UN_ASSIGNED)) {
			List<HelpBean> helpItems = getHelpItemsFromQueue(userId);

			if(helpItems.size() > 0) {
				return getHelpItemLists(helpItems, HelpMeContants.STATUS_OPEN);
			}
		} else {
			Optional<UserBean> userBean = user.findById(userId);
			if(userBean.isPresent()) { 
				if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_HELPFINDER)) {
					return getHelpItemLists(help.findByUserId(userId), helpItemStatus);
				} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_SERVICE_PROVIDER)) { 
					List<HelpBean> helpItems = help.findByAssignedUserId(userId);
					if(helpItemStatus == null || helpItemStatus.trim().length() == 0) {
						List<HelpBean> helpItemsQueue = getHelpItemsFromQueue(userId);
						if(helpItemsQueue.size() > 0) {
							helpItems.addAll(helpItemsQueue);
						}
					}
					return getHelpItemLists(helpItems, helpItemStatus);
				} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_VOLUNTEER)) {
					return getHelpItemLists(help.findByVolunteerUserId(userId), helpItemStatus);
				}
			}
		}
		return  new HelpListResponse();
	}

	private List<HelpBean> getHelpItemsFromQueue(int userId) {
		List<Integer> helpItems = new ArrayList<Integer>();
		List<HelpItemQueueBean> queue = helpItemQueue.findByUserId(userId);
		for (HelpItemQueueBean helpItem : queue) {
			helpItems.add(helpItem.getHelpItemId());
		}

		if(helpItems.size() > 0) {
			return (List<HelpBean>)help.findAllById(helpItems);
		}

		return new ArrayList<HelpBean>();
	}


	@Override
	public LoginBean generateAndSaveOTP(LoginBean loginBean) throws Exception {
		loginBean.setOtp(HelpMeUtil.generateOTP());
		loginBean = login.save(loginBean);
		HelpMeUtil.sendOTP(loginBean, env.getProperty(HelpMeContants.COMPANY_NAME), env.getProperty(HelpMeContants.PARAM3), env.getProperty(HelpMeContants.AUTHKEY), env.getProperty(HelpMeContants.SMS_URL), env.getProperty(HelpMeContants.TEMPLATE_ID));
		return loginBean;
	}

	@Override
	@Transactional
	public HelpBean assignHelp(int helpItemId, int userId) {
		Optional<HelpBean> helpBean = help.findById(helpItemId);
		if(helpBean.isPresent()) {
			Optional<UserBean> userBean = user.findById(userId);
			if(userBean.isPresent()) {
				HelpBean dbHelpBean = helpBean.get();
				if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_SERVICE_PROVIDER)) {
					dbHelpBean.setAssignedUserId(userId);
					helpItemQueue.deleteByHelpItemId(dbHelpBean.getId());
				} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_VOLUNTEER)) {
					dbHelpBean.setVolunteerUserId(userId);
				}
				help.save(dbHelpBean);
				return dbHelpBean;
			}
		}


		return null;
	}

	@Override
	@Transactional
	public HelpBean updateHelp(int helpItemId, int userId, String itemStatus) {
		Optional<HelpBean> helpBean = help.findById(helpItemId);
		if(helpBean.isPresent()) {
			Optional<UserBean> userBean = user.findById(userId);
			if(userBean.isPresent()) {
				HelpBean dbHelpBean = helpBean.get();
				if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_SERVICE_PROVIDER)) {
					dbHelpBean.setAssignedUserId(userId);
					helpItemQueue.deleteByHelpItemId(dbHelpBean.getId());					
				} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_VOLUNTEER)) {
					dbHelpBean.setVolunteerUserId(userId);
				}
				dbHelpBean.setUpdateBy(userId);
				dbHelpBean.setHelpItemStatus(itemStatus);
				dbHelpBean = help.save(dbHelpBean);
				HelpHistoryBean historyBean = new HelpHistoryBean();
				historyBean.setHelpItemId(dbHelpBean.getId());
				historyBean.setHelpItemStatus(dbHelpBean.getHelpItemStatus());
				historyBean.setHelpText(dbHelpBean.getHelpText());
				historyBean.setCreateDate(new Date());
				historyBean.setCreatedBy(userId);
				historyBean = helpHistory.save(historyBean);
				return dbHelpBean;
			}
		}


		return null;
	}

	@Override
	public List<UserBean> listServiceProvider(int cityId, int categoryId) {
		Optional<List<UserBean>> serviceProviders = user.getServiceProviders(cityId, categoryId, HelpMeContants.USER_TYPE_SERVICE_PROVIDER);
		if(serviceProviders.isPresent()) {
			return serviceProviders.get();
		}		
		return new ArrayList<UserBean>();
	}
	
	public List<HelpBean> getOpenHelpItems() {
		Optional<List<HelpBean>> openHelpItems = help.getOpenHelpItems();
		
		if(openHelpItems.isPresent()) {
			return openHelpItems.get();
		}
		return new ArrayList<HelpBean>();
	}
	
	private Map<Integer, Integer> getHelpUserCityMap(List<HelpBean> helpList) {
		Map<Integer, Integer> userCityMap = new HashMap<Integer, Integer>();
		
		List<UserBean> helpUsers = new ArrayList<UserBean>();
		List<Integer> helpUserIdList = new ArrayList<Integer>();
		
		for (HelpBean helpBean : helpList) {
			helpUserIdList.add(helpBean.getUserId());
		}
		
		if(helpUserIdList.size() > 0) {
			helpUsers =  (List<UserBean>)user.findAllById(helpUserIdList);
		}
		
		for (UserBean userBean : helpUsers) {
			userCityMap.put(userBean.getId(), userBean.getCityId());
		}
		return userCityMap;
	}
 
	public Map<Integer, List<HelpBean>> getCityHelpMap(List<HelpBean> helpList) {
		Map<Integer, List<HelpBean>> cityHelpMap = new HashMap<Integer, List<HelpBean>>();
		Map<Integer, Integer> userCityMap = getHelpUserCityMap(helpList);
		
		for (HelpBean helpBean : helpList) {
			List<HelpBean> cityHelpList = new ArrayList<HelpBean>();
			logger.debug("1 helpBean.getUserId(): " + helpBean.getUserId());
			logger.debug("userCityMap: " + userCityMap.size());
			logger.debug("2 helpBean.getUserId(): " + helpBean.getUserId());
			int cityId = userCityMap.get(helpBean.getUserId());
			if(cityHelpMap.containsKey(cityId)) {
				cityHelpList = cityHelpMap.get(cityId);
			}
			cityHelpList.add(helpBean);
			cityHelpMap.put(cityId, cityHelpList);
		}
		return cityHelpMap;
	}
	
	@Override
	public List<UserBean> getVolunteerUsers(int cityId) {
		Optional<List<UserBean>> volunteerUsers = user.getVolunteerUsers(cityId);
		if(volunteerUsers.isPresent()) {
			return volunteerUsers.get();
		}		
		return new ArrayList<UserBean>();
	}
	
	public int getNextVolunteerId(List<UserBean> volunteers) {
		int nextVolunteerId = 0;
		Map<Integer, Integer> userHelpCountMap = new HashMap<Integer, Integer>();
		
		for (UserBean userBean : volunteers) {
			userHelpCountMap.put(userBean.getId(), help.getVolunteerAssignedHelpCount(userBean.getId()));
		}		
		
		int minHelpCount = userHelpCountMap.get(volunteers.get(0).getId());
    	nextVolunteerId = volunteers.get(0).getId();
    	
    	for(int i=1;i<userHelpCountMap.size();i++){
		    if(userHelpCountMap.get(volunteers.get(i).getId()) < minHelpCount){
		    	minHelpCount = userHelpCountMap.get(volunteers.get(i).getId());
		    	nextVolunteerId = volunteers.get(i).getId();
			}
		}
		
		logger.debug("Volunteer Id with min help items: " + nextVolunteerId + " | No of help items: " + minHelpCount); 
		
		return nextVolunteerId;
	}
}
