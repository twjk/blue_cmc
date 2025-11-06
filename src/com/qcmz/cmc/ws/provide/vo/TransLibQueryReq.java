package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransLibQueryReq extends Request {
	private TransLibQueryBean bean = new TransLibQueryBean();

	public TransLibQueryBean getBean() {
		return bean;
	}

	public void setBean(TransLibQueryBean bean) {
		this.bean = bean;
	}
}
