package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobRequireUpdateReq extends Request {
	private LocalZhaopinJobRequireUpdateBean bean = new LocalZhaopinJobRequireUpdateBean();

	public LocalZhaopinJobRequireUpdateBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobRequireUpdateBean bean) {
		this.bean = bean;
	}
}
