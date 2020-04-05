package com.helpme.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpListResponse extends ResponseBean{
	
	private List<HelpBean> open;
	private List<HelpBean> accepted;
	private List<HelpBean> close;
	private List<HelpBean> rejected;
	private List<HelpBean> resolved;
	private List<HelpBean> unresolved;
	
	
	public HelpListResponse() {
		open = new ArrayList<HelpBean>();
		accepted = new ArrayList<HelpBean>();
		close = new ArrayList<HelpBean>();
		rejected = new ArrayList<HelpBean>();
		resolved = new ArrayList<HelpBean>();
		unresolved = new ArrayList<HelpBean>();
	}


	public List<HelpBean> getOpen() {
		return open;
	}


	public void setOpen(List<HelpBean> open) {
		this.open = open;
	}


	public List<HelpBean> getAccepted() {
		return accepted;
	}


	public void setAccepted(List<HelpBean> accepted) {
		this.accepted = accepted;
	}


	public List<HelpBean> getClose() {
		return close;
	}


	public void setClose(List<HelpBean> close) {
		this.close = close;
	}


	public List<HelpBean> getRejected() {
		return rejected;
	}


	public void setRejected(List<HelpBean> rejected) {
		this.rejected = rejected;
	}


	public List<HelpBean> getResolved() {
		return resolved;
	}


	public void setResolved(List<HelpBean> resolved) {
		this.resolved = resolved;
	}


	public List<HelpBean> getUnresolved() {
		return unresolved;
	}


	public void setUnresolved(List<HelpBean> unresolved) {
		this.unresolved = unresolved;
	}

	
}
