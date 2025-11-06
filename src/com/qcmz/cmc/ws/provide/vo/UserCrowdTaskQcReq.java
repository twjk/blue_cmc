package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskQcReq extends Request {
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审校人用户编号
	 */
	private Long qcUid;
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getQcUid() {
		return qcUid;
	}
	public void setQcUid(Long qcUid) {
		this.qcUid = qcUid;
	}
}
