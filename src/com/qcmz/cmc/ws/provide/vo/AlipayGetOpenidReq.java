package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Request;

public class AlipayGetOpenidReq extends Request {
	/**
	 * 授权码
	 */
	private String alipayAuthCode;

	public String getAlipayAuthCode() {
		return alipayAuthCode;
	}

	public void setAlipayAuthCode(String alipayAuthCode) {
		this.alipayAuthCode = alipayAuthCode;
	}
}
