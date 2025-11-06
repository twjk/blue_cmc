package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransTextAddReq extends Request {
	private TransTextAddBean bean = new TransTextAddBean();

	public TransTextAddBean getBean() {
		return bean;
	}

	public void setBean(TransTextAddBean bean) {
		this.bean = bean;
	}
}
