package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalTaskUserSubQueryReq extends Request {
	private LocalTaskUserSubQueryBean bean = new LocalTaskUserSubQueryBean();

	public LocalTaskUserSubQueryBean getBean() {
		return bean;
	}

	public void setBean(LocalTaskUserSubQueryBean bean) {
		this.bean = bean;
	}
}
