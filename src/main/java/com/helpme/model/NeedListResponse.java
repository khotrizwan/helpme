package com.helpme.model;

import java.util.List;
import java.util.Map;

public class NeedListResponse extends ResponseBean{
	
	private List<NeedBean> pending;
	private List<NeedBean> accepted;
	private List<NeedBean> complete;
	
	
	public NeedListResponse() {
		super();
	}

	public NeedListResponse(String errCode, String errMsg, List<NeedBean> pending, List<NeedBean> accepted, List<NeedBean> complete) {
		super(errCode, errMsg);
		this.pending = pending;
		this.accepted = accepted;
		this.complete = complete;
	}

	public List<NeedBean> getPending() {
		return pending;
	}

	public void setPending(List<NeedBean> pending) {
		this.pending = pending;
	}

	public List<NeedBean> getAccepted() {
		return accepted;
	}

	public void setAccepted(List<NeedBean> accepted) {
		this.accepted = accepted;
	}

	public List<NeedBean> getComplete() {
		return complete;
	}

	public void setComplete(List<NeedBean> complete) {
		this.complete = complete;
	}

	@Override
	public String toString() {
		return "NeedListResponse [pending=" + pending + ", accepted=" + accepted + ", complete=" + complete
				+ ", errCode=" + getErrCode() + ", errMsg=" + getErrMsg() + "]";
	}

}
