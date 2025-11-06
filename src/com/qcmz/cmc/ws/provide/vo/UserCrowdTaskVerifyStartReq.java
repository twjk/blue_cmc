package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskVerifyStartReq extends Request {
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审核人用户编号
	 */
	private Long verifyUid;
	public String getUtId() {
		return utId;
	}
	public void setUtId(String utId) {
		this.utId = utId;
	}
	public Long getVerifyUid() {
		return verifyUid;
	}
	public void setVerifyUid(Long verifyUid) {
		this.verifyUid = verifyUid;
	}
}
