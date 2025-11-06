package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskCancelReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 取消原因
	 */
	private String reason;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
