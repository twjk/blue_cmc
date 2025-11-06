package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class LocalTaskQueryReq extends Request {
	private LocalTaskQueryBean bean = new LocalTaskQueryBean();

	public LocalTaskQueryBean getBean() {
		return bean;
	}

	public void setBean(LocalTaskQueryBean bean) {
		this.bean = bean;
	}
}
