package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalZhaopinJobDutyUpdateReq extends Request {
	private LocalZhaopinJobDutyUpdateBean bean = new LocalZhaopinJobDutyUpdateBean();

	public LocalZhaopinJobDutyUpdateBean getBean() {
		return bean;
	}

	public void setBean(LocalZhaopinJobDutyUpdateBean bean) {
		this.bean = bean;
	}
}
