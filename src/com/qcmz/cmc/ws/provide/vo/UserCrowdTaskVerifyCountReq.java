package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskVerifyCountReq extends Request {
	/**
	 * 审核人用户编号
	 */
	private Long verifyUid;

	public Long getVerifyUid() {
		return verifyUid;
	}

	public void setVerifyUid(Long verifyUid) {
		this.verifyUid = verifyUid;
	}
}
