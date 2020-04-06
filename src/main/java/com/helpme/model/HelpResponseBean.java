package com.helpme.model;

public class HelpResponseBean extends HelpBean {

	UserBean helpFinder;
	UserBean orgUser;
	UserBean volunteer;
	OrgBean organisation;
	
	public HelpResponseBean() {
		super();
	}
	public HelpResponseBean(HelpBean helpBean) {
		super(helpBean);
	}
	public UserBean getHelpFinder() {
		return helpFinder;
	}
	public void setHelpFinder(UserBean helpFinder) {
		this.helpFinder = helpFinder;
	}
	
	public UserBean getOrgUser() {
		return orgUser;
	}
	public void setOrgUser(UserBean orgUser) {
		this.orgUser = orgUser;
	}
	public UserBean getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(UserBean volunteer) {
		this.volunteer = volunteer;
	}
	public OrgBean getOrganisation() {
		return organisation;
	}
	public void setOrganisation(OrgBean organisation) {
		this.organisation = organisation;
	}
	@Override
	public String toString() {
		return "HelpResponseBean [helpFinder=" + helpFinder + ", ordUser=" + orgUser + ", volunteer=" + volunteer
				+ ", organisation=" + organisation + ", " + super.toString() + "]";
	}
	
	
	
}
