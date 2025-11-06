package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class RewardBestowReq extends Request {
	private RewardBestowBean bean = new RewardBestowBean();

	public RewardBestowBean getBean() {
		return bean;
	}

	public void setBean(RewardBestowBean bean) {
		this.bean = bean;
	}	
}
