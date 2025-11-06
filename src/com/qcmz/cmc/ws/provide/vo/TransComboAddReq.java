package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class TransComboAddReq extends Request {
	private TransComboAddBean bean = new TransComboAddBean();

	public TransComboAddBean getBean() {
		return bean;
	}

	public void setBean(TransComboAddBean bean) {
		this.bean = bean;
	}
}
