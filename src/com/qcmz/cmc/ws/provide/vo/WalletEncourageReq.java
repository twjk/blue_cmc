package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class WalletEncourageReq extends Request {
	private WalletEncourageBean bean = new WalletEncourageBean();

	public WalletEncourageBean getBean() {
		return bean;
	}

	public void setBean(WalletEncourageBean bean) {
		this.bean = bean;
	}
}
