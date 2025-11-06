package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobAddressUpdateReq extends Request {
	private LocalZhaopinJobAddressUpdateBean bean = new LocalZhaopinJobAddressUpdateBean();

	public LocalZhaopinJobAddressUpdateBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobAddressUpdateBean bean) {
		this.bean = bean;
	}
}
