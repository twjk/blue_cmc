package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransLibAddReq extends Request {
	private TransLibAddBean bean = new TransLibAddBean();

	public TransLibAddBean getBean() {
		return bean;
	}

	public void setBean(TransLibAddBean bean) {
		this.bean = bean;
	}
	
}