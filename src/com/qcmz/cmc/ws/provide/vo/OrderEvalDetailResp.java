package com.qcmz.cmc.ws.provide.vo;

import com.qcmz.framework.ws.vo.Response;

public class OrderEvalDetailResp extends Response {
	private OrderEvalDetailBean result;

	public OrderEvalDetailBean getResult() {
		return result;
	}

	public void setResult(OrderEvalDetailBean result) {
		this.result = result;
	}
}
