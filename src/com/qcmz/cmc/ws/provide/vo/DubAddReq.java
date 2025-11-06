package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class DubAddReq extends Request {
	private DubAddBean bean = new DubAddBean();

	public DubAddBean getBean() {
		return bean;
	}

	public void setBean(DubAddBean bean) {
		this.bean = bean;
	}
}
