package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class CrowdTaskOrderAddReq extends Request {
	private CrowdTaskOrderAddBean bean = new CrowdTaskOrderAddBean();

	public CrowdTaskOrderAddBean getBean() {
		return bean;
	}

	public void setBean(CrowdTaskOrderAddBean bean) {
		this.bean = bean;
	}
}
