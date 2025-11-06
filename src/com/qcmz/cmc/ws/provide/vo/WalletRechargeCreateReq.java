package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class WalletRechargeCreateReq extends Request {
	private WalletRechargeCreateBean bean = new WalletRechargeCreateBean();

	public WalletRechargeCreateBean getBean() {
		return bean;
	}

	public void setBean(WalletRechargeCreateBean bean) {
		this.bean = bean;
	}
	
}
