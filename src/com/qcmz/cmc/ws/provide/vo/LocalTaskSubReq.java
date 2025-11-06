package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalTaskSubReq extends Request {
	private LocalTaskUserSubBean bean = new LocalTaskUserSubBean();

	public LocalTaskUserSubBean getBean() {
		return bean;
	}

	public void setBean(LocalTaskUserSubBean bean) {
		this.bean = bean;
	}
}
