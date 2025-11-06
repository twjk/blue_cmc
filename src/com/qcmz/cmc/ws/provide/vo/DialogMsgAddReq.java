package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class DialogMsgAddReq extends Request {
	private DialogMsgAddBean bean = new DialogMsgAddBean();

	public DialogMsgAddBean getBean() {
		return bean;
	}

	public void setBean(DialogMsgAddBean bean) {
		this.bean = bean;
	}
}
