package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransVideoFinishReq extends Request {
	private TransVideoDealBean bean = new TransVideoDealBean();
	
	public TransVideoDealBean getBean() {
		return bean;
	}

	public void setBean(TransVideoDealBean bean) {
		this.bean = bean;
	}
}
