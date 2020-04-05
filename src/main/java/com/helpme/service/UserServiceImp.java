package com.helpme.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.helpme.config.HelpMeContants;
import com.helpme.model.HelpBean;
import com.helpme.model.HelpHistoryBean;
import com.helpme.model.HelpItemQueueBean;
import com.helpme.model.HelpListResponse;
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
		Optional<LoginBean> bdDetails = login.findById(userBean.getMobileno());
		if(bdDetails.isPresent() && bdDetails.get().getOtp().equals(userBean.getOtp())) {
			userBean.setIsAdmin(HelpMeContants.Y);
			userBean.setUserType(HelpMeContants.USER_TYPE_HELPFINDER); //help_finder / service provider / volunteer / HelpMePlease 
			userBean.setOrganizationId(1);
			userBean.setIsActive(HelpMeContants.Y);
			userBean.setCreateDate(new Date());
			return user.save(userBean);
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
			System.out.println(orgBean.getId());

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
			System.out.println("In  saveServiceProvider Org ID: " + orgBean.getId());
			saveServiceProviderUser(userBean);	
			System.out.println("In  saveServiceProvider User ID: "+ userBean.getId());

			return userBean;
		} 
		return null;

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
			List<OrgHelpCategory> serviceProvidersForCat = orgHelpCat.findByHelpCategory(helpBean.getHelpCategoryId());
			
			for(OrgHelpCategory serviceProviderCat:serviceProvidersForCat)
			{
				Optional<OrgBean> serviceProvider =   org.findById(serviceProviderCat.getOrganizationId());
				if(serviceProvider.isPresent())
				{
					serviceProviders.add(serviceProvider.get());
				}
			}
			
			// Same City
			for(OrgBean serviceProvider:serviceProviders)
			{
				// Checking SP's are of Seeker's city
				if(serviceProvider.getCityId() == requestSeekerBean.getCityId())
				{
					spsToSeekHelp.add(serviceProvider);
				}
			}
			
			// Checking size
			if(spsToSeekHelp.size() == 1)
			{
				HelpItemQueueBean bean = new HelpItemQueueBean();
				bean.setHelpItemId(helpBean.getId());
				bean.setUserId(helpBean.getId());
				helpItemQueue.save(bean);
				return helpBean;
			}
			
			if (spsToSeekHelp.size() < 10)
			{
				insertIntoQueue(helpBean, spsToSeekHelp);
				return helpBean;
			}
			
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
				// Assigning to all service providers within 3 km range
				insertIntoQueue(helpBean, spsToSeekHelp);
				return helpBean;
			}
			
			// In worst case assign to all service providers for category selected
			insertIntoQueue(helpBean, serviceProviders);
			return helpBean;
			
		}
		
		return null;
	}
	
	private void insertIntoQueue(HelpBean helpBean, List<OrgBean> serviceProviders)
	{
		for(OrgBean serviceProvider:serviceProviders)
		{
			HelpItemQueueBean bean = new HelpItemQueueBean();
			bean.setHelpItemId(helpBean.getId());
			bean.setUserId(serviceProvider.getId());
			helpItemQueue.save(bean);
		}
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
		List<HelpBean> pending = new ArrayList<HelpBean>();
		List<HelpBean> accepted = new ArrayList<HelpBean>();
		List<HelpBean> close = new ArrayList<HelpBean>();
		List<HelpBean> rejected = new ArrayList<HelpBean>();
		List<HelpBean> resolved = new ArrayList<HelpBean>();
		List<HelpBean> unresolved = new ArrayList<HelpBean>();

		for (HelpBean helpBean : helps) {
			String key = helpBean.getHelpItemStatus();
			if((helpItemStatus==null || helpItemStatus.equals(HelpMeContants.STATUS_OPEN)) && key.equalsIgnoreCase(HelpMeContants.STATUS_OPEN)) {
				pending.add(helpBean);
			} else if((helpItemStatus==null || helpItemStatus.equals(HelpMeContants.STATUS_ACCEPTED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_ACCEPTED)) {
				accepted.add(helpBean);
			} else if((helpItemStatus==null || helpItemStatus.equals(HelpMeContants.STATUS_CLOSED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_CLOSED)) {
				close.add(helpBean);
			} else if((helpItemStatus==null || helpItemStatus.equals(HelpMeContants.STATUS_REJECTED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_REJECTED)) {
				rejected.add(helpBean);
			} else if((helpItemStatus==null || helpItemStatus.equals(HelpMeContants.STATUS_RESOLVED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_RESOLVED)) {
				resolved.add(helpBean);
			} else if((helpItemStatus==null || helpItemStatus.equals(HelpMeContants.STATUS_UN_RESOLVED)) && key.equalsIgnoreCase(HelpMeContants.STATUS_UN_RESOLVED)) {
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
	public HelpListResponse userHelpList(int userId, String helpItemStatus) {
		if(helpItemStatus != null && helpItemStatus.equals(HelpMeContants.STATUS_UN_ASSIGNED)) {
			List<HelpItemQueueBean> queue = helpItemQueue.findByUserId(userId);
			List<Integer> helpItems = new ArrayList<>();
			for (HelpItemQueueBean helpItem : queue) {
				helpItems.add(helpItem.getHelpItemId());
			}
			if(helpItems.size() > 0) {
				return getHelpItemLists((List<HelpBean>)help.findAllById(helpItems), HelpMeContants.STATUS_OPEN);
			} else {
				return  new HelpListResponse();
			}
		} else {
			Optional<UserBean> userBean = user.findById(userId);
			if(userBean.isPresent()) { 
				if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_HELPFINDER)) {
					return getHelpItemLists(help.findByUserId(userId), helpItemStatus);
				} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_SERVICE_PROVIDER)) { 
					return getHelpItemLists(help.findByAssignedUserId(userId), helpItemStatus);
				} else if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_VOLUNTEER)) {
					return getHelpItemLists(help.findByVolunteerUserId(userId), helpItemStatus);
				}
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

	@Override
	public HelpBean assignHelp(int helpItemId, int userId) {
		Optional<HelpBean> helpBean = help.findById(helpItemId);
		if(helpBean.isPresent()) {
			Optional<UserBean> userBean = user.findById(userId);
			if(userBean.isPresent()) {
				HelpBean dbHelpBean = helpBean.get();
				if(userBean.get().getUserType().equals(HelpMeContants.USER_TYPE_SERVICE_PROVIDER)) {
					dbHelpBean.setAssignedUserId(userId);
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
}
