package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobBasicUpdateReq extends Request {
	private LocalZhaopinJobBasicUpdateBean bean = new LocalZhaopinJobBasicUpdateBean();

	public LocalZhaopinJobBasicUpdateBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobBasicUpdateBean bean) {
		this.bean = bean;
	}
}
