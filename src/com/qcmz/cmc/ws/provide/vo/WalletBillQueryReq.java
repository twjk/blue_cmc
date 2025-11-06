package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：账单查询
 * 修改历史：
 */
public class WalletBillQueryReq extends Request {
	private WalletBillQueryBean bean = new WalletBillQueryBean();

	public WalletBillQueryBean getBean() {
		return bean;
	}

	public void setBean(WalletBillQueryBean bean) {
		this.bean = bean;
	}
}
