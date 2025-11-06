package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobQueryReq extends Request {
	private LocalZhaopinJobQueryBean bean = new LocalZhaopinJobQueryBean();

	public LocalZhaopinJobQueryBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobQueryBean bean) {
		this.bean = bean;
	}
}
