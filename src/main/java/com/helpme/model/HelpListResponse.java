package com.helpme.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpListResponse extends ResponseBean{
	
	private List<HelpResponseBean> open;
	private List<HelpResponseBean> accepted;
	private List<HelpResponseBean> close;
	private List<HelpResponseBean> rejected;
	private List<HelpResponseBean> resolved;
	private List<HelpResponseBean> unresolved;
	
	
	public HelpListResponse() {
		open = new ArrayList<HelpResponseBean>();
		accepted = new ArrayList<HelpResponseBean>();
		close = new ArrayList<HelpResponseBean>();
		rejected = new ArrayList<HelpResponseBean>();
		resolved = new ArrayList<HelpResponseBean>();
		unresolved = new ArrayList<HelpResponseBean>();
	}


	public List<HelpResponseBean> getOpen() {
		return open;
	}


	public void setOpen(List<HelpResponseBean> open) {
		this.open = open;
	}


	public List<HelpResponseBean> getAccepted() {
		return accepted;
	}


	public void setAccepted(List<HelpResponseBean> accepted) {
		this.accepted = accepted;
	}


	public List<HelpResponseBean> getClose() {
		return close;
	}


	public void setClose(List<HelpResponseBean> close) {
		this.close = close;
	}


	public List<HelpResponseBean> getRejected() {
		return rejected;
	}


	public void setRejected(List<HelpResponseBean> rejected) {
		this.rejected = rejected;
	}


	public List<HelpResponseBean> getResolved() {
		return resolved;
	}


	public void setResolved(List<HelpResponseBean> resolved) {
		this.resolved = resolved;
	}


	public List<HelpResponseBean> getUnresolved() {
		return unresolved;
	}


	public void setUnresolved(List<HelpResponseBean> unresolved) {
		this.unresolved = unresolved;
	}


	
}
