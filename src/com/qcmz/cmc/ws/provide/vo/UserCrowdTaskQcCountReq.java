package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class UserCrowdTaskQcCountReq extends Request {
	/**
	 * 审校人用户编号
	 */
	private Long qcUid;

	public Long getQcUid() {
		return qcUid;
	}

	public void setQcUid(Long qcUid) {
		this.qcUid = qcUid;
	}
}
