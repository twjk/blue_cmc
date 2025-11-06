package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskStartReq extends Request {
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户任务编号
	 */
	private String utId;
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
}
