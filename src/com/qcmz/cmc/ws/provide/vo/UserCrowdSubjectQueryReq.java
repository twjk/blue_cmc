package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdSubjectQueryReq extends Request {
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审校编号
	 */
	private Long qcId;

	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}

	public Long getQcId() {
		return qcId;
	}

	public void setQcId(Long qcId) {
		this.qcId = qcId;
	}
}
