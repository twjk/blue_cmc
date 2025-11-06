package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobExpUpdateReq extends Request {
	private LocalZhaopinJobExpUpdateBean bean = new LocalZhaopinJobExpUpdateBean();

	public LocalZhaopinJobExpUpdateBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobExpUpdateBean bean) {
		this.bean = bean;
	}
}
