package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RewardCashApplyReq extends Request {
	private RewardCashApplyBean bean = new RewardCashApplyBean();

	public RewardCashApplyBean getBean() {
		return bean;
	}

	public void setBean(RewardCashApplyBean bean) {
		this.bean = bean;
	}
}
