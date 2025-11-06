package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobAddReq extends Request {
	private LocalZhaopinJobAddBean bean = new LocalZhaopinJobAddBean();

	public LocalZhaopinJobAddBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobAddBean bean) {
		this.bean = bean;
	}
}
