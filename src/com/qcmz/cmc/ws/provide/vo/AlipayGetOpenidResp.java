package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class AlipayGetOpenidResp extends Response {
	private AlipayOpenidBean result;

	public AlipayOpenidBean getResult() {
		return result;
	}

	public void setResult(AlipayOpenidBean result) {
		this.result = result;
	}
}
