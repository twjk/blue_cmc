package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RewardInviteReq extends Request {
	private RewardInviteBean bean = new RewardInviteBean();

	public RewardInviteBean getBean() {
		return bean;
	}

	public void setBean(RewardInviteBean bean) {
		this.bean = bean;
	}	
}
