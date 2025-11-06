package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransUserCorrectReq extends Request {
	private TransUserCorrectBean bean = new TransUserCorrectBean();

	public TransUserCorrectBean getBean() {
		return bean;
	}

	public void setBean(TransUserCorrectBean bean) {
		this.bean = bean;
	}
}
