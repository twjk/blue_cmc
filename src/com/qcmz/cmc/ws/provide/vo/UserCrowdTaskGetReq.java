package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskGetReq extends Request {
	/**
	 * 用户任务编号
	 */
	private String utId;

	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}
}
